package com.alexs7;

import de.bwaldvogel.liblinear.SolverType;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.dense.gradient.dsift.DenseSIFT;
import org.openimaj.image.feature.dense.gradient.dsift.FloatDSIFTKeypoint;
import org.openimaj.image.feature.dense.gradient.dsift.PyramidDenseSIFT;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LinearSVMAnnotator;
import org.openimaj.ml.kernel.HomogeneousKernelMap;

import java.util.List;
import java.util.Map;

/**
 * Created by alex on 15/03/2016.
 */
public class Runner {

    public static void runKNNClassifier(VFSGroupDataset<FImage> trainingImagesDataset, Map<String, FImage> testingImagesDataset) {
        KNNClassifier knnClassifier = new KNNClassifier(trainingImagesDataset);
        String category;
        String imageName;
        ResizeProcessor resizeProcessor = new ResizeProcessor(16,16);
        ResultFileWriter resultFileWriter = new ResultFileWriter("run1.txt");
        double[] imageFeatureVector;

        for (Map.Entry<String, FImage> testImageEntry : testingImagesDataset.entrySet() ) {
            FImage tinyImage = Utilities.squareImage(testImageEntry.getValue());
            tinyImage.processInplace(resizeProcessor);

            imageFeatureVector = Utilities.getFeaturesVector(tinyImage);
            imageFeatureVector = Utilities.zeroMean(imageFeatureVector);
            imageFeatureVector = Utilities.normaliseVector(imageFeatureVector);

            category = knnClassifier.classify(imageFeatureVector);
            imageName = testImageEntry.getKey();
            resultFileWriter.writeResultToFile(imageName,category);
        }
    }

    public static void runLinearClassifiers(VFSGroupDataset<FImage> trainingImagesDataset, Map<String, FImage> testingImagesDataset) {

        double[][] bagOfVisualFeatures;
        int numberOfClusters = 500;
        PatchesCodeBook codeBook;
        int limit = 25; // pick number 'limit' images from each category.
        ResultFileWriter resultFileWriter = new ResultFileWriter("run2.txt");
        ClassificationResult<String> result = null;

        bagOfVisualFeatures = Utilities.getBOVFFromTrainingImageDescriptors(trainingImagesDataset,limit);
        codeBook = new PatchesCodeBook(bagOfVisualFeatures,numberOfClusters);

        FeatureExtractor<DoubleFV, FImage> extractor = new DescriptorExtractor(codeBook);

        //The classifier mode; either multiclass or multilabel. Multiclass mode will use liblinear's internal multiclass support,
        // whereas multilabel mode will create a set of one-versus-all (OvA) classifiers for each class.
        LiblinearAnnotator<FImage, String> ann = new LiblinearAnnotator<FImage, String>(
                extractor, LiblinearAnnotator.Mode.MULTILABEL, SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);

        ann.train(trainingImagesDataset);

        for (Map.Entry<String, FImage> testImageEntry : testingImagesDataset.entrySet() ) {
            result = ann.classify(testImageEntry.getValue());
            result.getPredictedClasses().iterator().next();
            resultFileWriter.writeResultToFile(testImageEntry.getKey(),result.getPredictedClasses().iterator().next().toLowerCase());
        }
    }

    public static void runBestClassifier(VFSGroupDataset<FImage> trainingImagesDataset, Map<String, FImage> testingImagesDataset) {

        List<LocalFeatureList<FloatDSIFTKeypoint>> bagOfVisualFeatures;
        int numberOfClusters = 300;
        int limit = 25; // pick number 'limit' images from each category.
        SIFTCodebook codeBook;
        DenseSIFT dsift = new DenseSIFT(5, 7);
        PyramidDenseSIFT<FImage> pdsift = new PyramidDenseSIFT<FImage>(dsift, 6f, 7);
        ResultFileWriter resultFileWriter = new ResultFileWriter("run3.txt");
        ClassificationResult<String> result = null;

        bagOfVisualFeatures = Utilities.getBOVFFromDenseSIFT(trainingImagesDataset, limit, dsift, pdsift);
        codeBook = new SIFTCodebook(bagOfVisualFeatures,numberOfClusters);

        HomogeneousKernelMap homogeneousKernelMap = new HomogeneousKernelMap(HomogeneousKernelMap.KernelType.Chi2, 1.0,  HomogeneousKernelMap.WindowType.Rectangular);
        FeatureExtractor<DoubleFV, FImage> extractor = new PHOWExtractor(codeBook,pdsift);
        FeatureExtractor<DoubleFV, FImage> homogeneousKernelMapWrappedExtractor = homogeneousKernelMap.createWrappedExtractor(extractor);

        LinearSVMAnnotator<FImage, String> ann = new LinearSVMAnnotator<FImage, String>(homogeneousKernelMapWrappedExtractor);

        ann.train(trainingImagesDataset);

        for (Map.Entry<String, FImage> testImageEntry : testingImagesDataset.entrySet() ) {
            result = ann.classify(testImageEntry.getValue());
            if(!result.getPredictedClasses().isEmpty()){
                resultFileWriter.writeResultToFile(testImageEntry.getKey(),result.getPredictedClasses().iterator().next().toLowerCase());
            }else{
                resultFileWriter.writeResultToFile(testImageEntry.getKey(),"failed to classify");
            }
        }
    }
}
