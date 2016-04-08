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

        ImageLoader trainingImageLoader = new ImageLoader("/home/ar1v13/Projects/UniWork/SceneRecognition/training");
        ImageLoader testingImageLoader = new ImageLoader("/home/ar1v13/Projects/UniWork/SceneRecognition/testing");

        VFSGroupDataset<FImage> trainingImagesDataset = trainingImageLoader.getGroupDataSet();
        Map<String,FImage> testingImagesDataset = testingImageLoader.getMapDataSet();

//        Run #1
//        Runner.runKNNClassifier(trainingImagesDataset,testingImagesDataset);
//        Run #2
        Runner.runLinearClassifiers(trainingImagesDataset,testingImagesDataset);

    }

}
