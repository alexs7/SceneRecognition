package com.alexs7;

import de.bwaldvogel.liblinear.SolverType;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.experiment.evaluation.classification.ClassificationEvaluator;
import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMAnalyser;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMResult;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.openimaj.ml.annotation.Annotated;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            imageFeatureVector = Utilities.unitLength(imageFeatureVector);

            category = knnClassifier.classify(imageFeatureVector);
            imageName = testImageEntry.getKey();
            resultFileWriter.writeResultToFile(imageName,category);
        }
    }

    public static void runLinearClassifiers(VFSGroupDataset<FImage> trainingImagesDataset, Map<String, FImage> testingImagesDataset) {

        double[][] bagOfVisualWords;
        int numberOfClusters = 500;
        CodeBook codeBook;
        int limit = 25; // pick number 'limit' images from each category.
        ResultFileWriter resultFileWriter = new ResultFileWriter("run2.txt");
        ClassificationResult<String> result = null;

        bagOfVisualWords = Utilities.getBOVWFromTrainingImageDescriptors(trainingImagesDataset,limit);
        codeBook = new CodeBook(bagOfVisualWords,numberOfClusters);

        FeatureExtractor<DoubleFV, FImage> extractor = new DescriptorExtractor(codeBook);

        LiblinearAnnotator<FImage, String> ann = new LiblinearAnnotator<FImage, String>(
                extractor, LiblinearAnnotator.Mode.MULTICLASS, SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);

        ann.train(trainingImagesDataset);

        for (Map.Entry<String, FImage> testImageEntry : testingImagesDataset.entrySet() ) {
            result = ann.classify(testImageEntry.getValue());
            result.getPredictedClasses().iterator().next();
            resultFileWriter.writeResultToFile(testImageEntry.getKey(),result.getPredictedClasses().iterator().next().toLowerCase());
        }
    }
}
