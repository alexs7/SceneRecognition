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

    private int clusters;
    private double[][] bagOfVisualWords;
    private HardAssigner<double[],?,?> assigner;
    double[][] representantiveVectors;

    public CodeBook(double[][] bagOfVisualWords, int clusters) {
        this.clusters = clusters;
        this.bagOfVisualWords = bagOfVisualWords;

        DoubleKMeans doubleKMeans = DoubleKMeans.createExact(clusters);
        DoubleCentroidsResult centroidsResults = doubleKMeans.cluster(bagOfVisualWords);
        this.assigner = centroidsResults.defaultHardAssigner();

        this.representantiveVectors = centroidsResults.getCentroids();
    }

    public double[] getRepresentativeVector(double[] descriptor) {
        int index = assigner.assign(descriptor);
        return representantiveVectors[index];
    }
}
