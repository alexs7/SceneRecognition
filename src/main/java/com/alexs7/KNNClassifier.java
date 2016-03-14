package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alex on 14/03/2016.
 */
public class KNNClassifier {

    private VFSGroupDataset<FImage> trainingData;
    private VFSListDataset<FImage> testingData;
    private ArrayList<String> categories;

    public KNNClassifier(VFSGroupDataset<FImage> trainingImagesDataset, VFSListDataset<FImage> testingImagesDataset) {
        setTestingData(testingImagesDataset);
        setTrainingData(trainingImagesDataset);
        setCategories(trainingImagesDataset);
    }

    public void setTrainingData(VFSGroupDataset<FImage> trainingData) {
        this.trainingData = trainingData;
    }

    public void setTestingData(VFSListDataset<FImage> testingData) {
        this.testingData = testingData;
    }

    public String classify(double[] imageToClassify) {
        String category = null;

        for (Map.Entry<String, VFSListDataset<FImage>> trainingImage : trainingData.entrySet()) {
        }

        return category;

    }

    public void setCategories(VFSGroupDataset<FImage> trainingImagesDataset) {
        ArrayList<String> categories = new ArrayList<>();
        categories.addAll(trainingImagesDataset.getGroups());
        this.categories = categories;
    }
}
