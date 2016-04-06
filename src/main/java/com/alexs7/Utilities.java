package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.FImage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alex on 14/03/2016.
 */
public class Utilities {
    public static FImage squareImage(FImage image) {
        int newWidth = 0;
        int newHeight = 0;

        if(image.getWidth() > image.getHeight()){
            newWidth = image.getHeight();
            newHeight = image.getHeight();
        }else{
            newWidth = image.getWidth();
            newHeight = image.getWidth();
        }

        return image.extractCentreSubPix(image.getWidth()/2,image.getHeight()/2, new FImage(new float[newHeight][newWidth])); // TODO double check this maybe use extractROI?
    }

    public static double[] zeroMean(double[] vector) { //will return an array of doubles
        double sum = 0;

        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }

        double mean = sum / vector.length;

        for (int i = 0; i < vector.length; i++) {
            vector[i] = vector[i] - mean;
        }

        return vector;
    }

    public static double[] getFeaturesVector(FImage image) {
        return image.getDoublePixelVector();
    }

    public static double[] unitLength(double[] vector) {
        double magnitude = 0;
        double sum = 0;

        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(vector[i], 2);
        }

        magnitude = Math.sqrt(sum);

        for (int i = 0; i < vector.length; i++) {
            vector[i] = vector[i]/magnitude;
        }

        return vector;
    }

    public static ArrayList<double[]> getImageDescriptors(FImage image) { //using the patches from the coursework instructions
        ArrayList<double[]> imageDescriptors = new ArrayList<>();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        FImage subimage = null;

        for (int y = 0; y < image.getHeight(); y+=4) {
            for (int x = 0; x < image.getWidth(); x+=4) {
                if(x+8 < imageWidth && y+8 < imageHeight ) {
                    subimage = image.extractROI(x, y, 8, 8);
                    imageDescriptors.add(getFeaturesVector(subimage));
                }
            }
        }
        return imageDescriptors;
    }

    public static double[][] getBOVWFromTrainingImageDescriptors(VFSGroupDataset<FImage> trainingImagesDataset, int limit) {
        ArrayList<double[]> imageDescriptors;
        ArrayList<double[]> bagOfVisualWords = new ArrayList<>();
        int bagOfVWHeight = 0;
        int bagOfVWWidth = 0;

        for (Map.Entry<String, VFSListDataset<FImage>> mapEntry : trainingImagesDataset.entrySet() ) {
            for (FImage trainingImage : mapEntry.getValue().subList(0,limit)){

                imageDescriptors = getImageDescriptors(trainingImage);
                for (int i = 0; i < imageDescriptors.size(); i++) {
                    bagOfVisualWords.add(imageDescriptors.get(i));
                }
            }
        }

        double[][] bagOfVisualWordsDoubleArray = new double[bagOfVisualWords.size()][bagOfVisualWords.get(0).length]; // width will be always 64

        for (int i = 0; i < bagOfVisualWords.size(); i++) {
            bagOfVisualWordsDoubleArray[i] = bagOfVisualWords.get(i);
        }

        return bagOfVisualWordsDoubleArray;
    }


    public static int[] getFixedLengthVectorWordOccurences(CodeBook codebook, FImage input) {
        ArrayList<double[]> imageDescriptors = Utilities.getImageDescriptors(input);
        double[][] representantiveVectors = codebook.getRepresentantiveVectors();
        int[] fixedLengthVector = new int[codebook.size()];
        int representativeVectorIndex;

        for (double[] imageDescriptor : imageDescriptors){
            representativeVectorIndex = codebook.getRepresentativeVectorIndexFromDescriptor(imageDescriptor);
            fixedLengthVector[representativeVectorIndex] +=1;
        }

        return fixedLengthVector;
    }
}
