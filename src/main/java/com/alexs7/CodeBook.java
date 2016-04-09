package com.alexs7;

import org.openimaj.ml.clustering.DoubleCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.DoubleKMeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 05/04/2016.
 */
public class CodeBook {

    private HardAssigner<double[],?,?> assigner;
    double[][] representantiveVectors;

    public CodeBook(double[][] bagOfVisualWords, int clusters) {

        DoubleKMeans doubleKMeans = DoubleKMeans.createExact(clusters);
        DoubleCentroidsResult centroidsResults = doubleKMeans.cluster(bagOfVisualWords);
        this.assigner = centroidsResults.defaultHardAssigner();

        this.representantiveVectors = centroidsResults.getCentroids();
    }

    public double[] getRepresentativeVectorFromDescriptor(double[] descriptor) {
        int index = assigner.assign(descriptor);
        return representantiveVectors[index];
    }

    public int getRepresentativeVectorIndexFromDescriptor(double[] descriptor) {
        return assigner.assign(descriptor);
    }

    public double[][] getRepresentantiveVectors() {
        return representantiveVectors;
    }

    public int size() {
        return representantiveVectors.length;
    }
}
