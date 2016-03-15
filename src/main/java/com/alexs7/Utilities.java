package com.alexs7;

import org.la4j.vector.dense.BasicVector;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;

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

        return image.extractCentreSubPix(image.getWidth()/2,image.getHeight()/2, new FImage(new float[newHeight][newWidth]));
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
}
