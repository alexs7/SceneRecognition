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
public class PatchesCodeBook {

    private HardAssigner<double[],?,?> assigner;
    private double[][] representantiveVectors;

    public PatchesCodeBook(double[][] bagOfVisualWords, int clusters) {

        DoubleKMeans doubleKMeans = DoubleKMeans.createExact(clusters);
        DoubleCentroidsResult centroidsResults = doubleKMeans.cluster(bagOfVisualWords);

        this.assigner = centroidsResults.defaultHardAssigner();
        this.representantiveVectors = centroidsResults.getCentroids();
    }

    public int getRepresentativeVectorIndexFromDescriptor(double[] descriptor) {
        return assigner.assign(descriptor);
    }

    public int size() {
        return representantiveVectors.length;
    }
}
