package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.image.FImage;

import java.io.IOException;
import java.util.Map;

/**
 * Created by alex on 08/03/2016.
 */
public class App {

    public static void main(String[] args) throws IOException {

        ImageLoader trainingImageLoader = new ImageLoader("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/SceneRecognition/training");
        ImageLoader testingImageLoader = new ImageLoader("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/SceneRecognition/testing");

        VFSGroupDataset<FImage> trainingImagesDataset = trainingImageLoader.getGroupDataSet();
        Map<String,FImage> testingImagesDataset = testingImageLoader.getMapDataSet();

//        Run #1
//        Runner.runKNNClassifier(trainingImagesDataset,testingImagesDataset);
//        Run #2
 //       Runner.runLinearClassifiers(trainingImagesDataset,testingImagesDataset);
//        Run #3
        Runner.runBestClassifier(trainingImagesDataset,testingImagesDataset);

        System.out.println("Done!");

    }

}
