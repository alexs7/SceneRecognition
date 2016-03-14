package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.processing.resize.ResizeProcessor;

import java.io.IOException;
import java.util.Map;

/**
 * Created by alex on 08/03/2016.
 */
public class App {

    public static void main(String[] args) throws IOException {
        ImageLoader trainingImageLoader = new ImageLoader("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/SceneRecognition/training/");
        ImageLoader testingImageLoader = new ImageLoader("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/SceneRecognition/training/");

        VFSGroupDataset<FImage> trainingImagesDataset = trainingImageLoader.getGroupDataSet();
        VFSListDataset<FImage> testingImagesDataset = trainingImageLoader.getListDataSet();

        KNNClassifier knnClassifier = new KNNClassifier(trainingImagesDataset,testingImagesDataset);

        String category;
        ResizeProcessor resizeProcessor = new ResizeProcessor(16,16);
        double[] imageFeatureVector;
        //for (FImage testingImage : testingImagesDataset) {
            FImage testingImage = Utilities.squareImage(testingImagesDataset.get(19));
            testingImage.processInplace(resizeProcessor); //or 'tiny image'

            imageFeatureVector = Utilities.getFeaturesVector(testingImage);
            imageFeatureVector = Utilities.zeroMean(imageFeatureVector);
            imageFeatureVector = Utilities.unitLength(imageFeatureVector);

            category = knnClassifier.classify(imageFeatureVector);
        //}

    }

}
