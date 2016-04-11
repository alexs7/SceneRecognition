package com.alexs7;

import org.openimaj.data.DataSource;
import org.openimaj.feature.local.data.LocalFeatureListDataSource;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.feature.dense.gradient.dsift.FloatDSIFTKeypoint;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;
import org.openimaj.util.pair.IntFloatPair;

import java.util.List;

/**
 * Created by alex on 11/04/2016.
 */
public class SIFTCodebook {

    private final float[][] representantiveVectors;
    private HardAssigner<float[], float[], IntFloatPair> assigner;

    public SIFTCodebook(List<LocalFeatureList<FloatDSIFTKeypoint>> bagOfVisualFeatures, int numberOfClusters) {
        FloatKMeans floatKMeans = FloatKMeans.createExact(numberOfClusters);
        DataSource<float[]> datasource = new LocalFeatureListDataSource<FloatDSIFTKeypoint, float[]>(bagOfVisualFeatures);
        FloatCentroidsResult centroidsResults = floatKMeans.cluster(datasource);

        this.assigner = centroidsResults.defaultHardAssigner();
        this.representantiveVectors = centroidsResults.getCentroids();
    }

    public int getRepresentativeVectorIndexFromDescriptor(float[] descriptor) {
        return assigner.assign(descriptor);
    }

    public int size() {
        return representantiveVectors.length;
    }
}
