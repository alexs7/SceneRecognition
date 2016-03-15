package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.FImage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alex on 14/03/2016.
 */
public class KNNClassifier {

    private VFSGroupDataset<FImage> trainingData;
    private ArrayList<String> categories;

    public KNNClassifier(VFSGroupDataset<FImage> trainingImagesDataset) {
        setTrainingData(trainingImagesDataset);
        setCategories(trainingImagesDataset);
    }

    public void setTrainingData(VFSGroupDataset<FImage> trainingData) {
        this.trainingData = trainingData;
    }

    public String classify(double[] testImageFeatureVector) {
        String category = null;
        double distance = 0;
        int k = (int) Math.ceil(Math.sqrt(testImageFeatureVector.length));
        ArrayList<Result> results = new ArrayList<>();
        double[] trainingImageFeatureVector;

        for (Map.Entry<String, VFSListDataset<FImage>> trainingImageKeyValue : trainingData.entrySet()) {
            category = trainingImageKeyValue.getKey();
            for (FImage trainingImage : trainingImageKeyValue.getValue()){

                trainingImageFeatureVector = Utilities.getFeaturesVector(trainingImage);
                trainingImageFeatureVector = Utilities.zeroMean(trainingImageFeatureVector);
                trainingImageFeatureVector = Utilities.unitLength(trainingImageFeatureVector);

                distance = getDistance(testImageFeatureVector,trainingImageFeatureVector);
                results.add(new Result(category,distance));
            }
        }

        Comparator<Result> byDistance = (r1, r2) -> Double.compare(r1.getDistance(), r2.getDistance());
        results = results.stream().sorted(byDistance.reversed()).collect(Collectors.toCollection(ArrayList::new));

        category = getCategory(results,k);

        return category;
    }

    private String getCategory(ArrayList<Result> arrayList, int k) {
        List<Result> neighbours = arrayList.subList(0,k);
        ArrayList<String> neighboursCategories = new ArrayList<>();
        String mostFrequentCategory = null;
        int minFrequency = Integer.MIN_VALUE;

        for (Result neighbour : neighbours){
            neighboursCategories.add(neighbour.getCategory());
        }

        for (String category : categories){
            int frequency = Collections.frequency(neighboursCategories,category);
            if(frequency > minFrequency){
                minFrequency = frequency;
                mostFrequentCategory = category;
            }
        }

        return mostFrequentCategory;
    }

    private double getDistance(double[] v1, double[] v2) {
        double squared_distance = 0;
        for (int i = 0; i < v1.length; i++) {
            squared_distance += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(squared_distance);
    }

    public void setCategories(VFSGroupDataset<FImage> trainingImagesDataset) {
        ArrayList<String> categories = new ArrayList<>();
        categories.addAll(trainingImagesDataset.getGroups());
        this.categories = categories;
    }
}
