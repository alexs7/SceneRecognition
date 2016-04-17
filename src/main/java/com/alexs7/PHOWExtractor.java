package com.alexs7;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.feature.SparseIntFV;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.dense.gradient.dsift.DenseSIFT;
import org.openimaj.image.feature.dense.gradient.dsift.FloatDSIFTKeypoint;
import org.openimaj.image.feature.dense.gradient.dsift.PyramidDenseSIFT;
import org.openimaj.image.feature.local.aggregate.BagOfVisualWords;
import org.openimaj.image.feature.local.aggregate.BlockSpatialAggregator;

import java.util.ArrayList;

/**
 * Created by alex on 11/04/2016.
 */
public class PHOWExtractor implements FeatureExtractor<DoubleFV, FImage> {

    private PyramidDenseSIFT<FImage> pdsift;
    private SIFTCodebook codeBook;

    public PHOWExtractor(SIFTCodebook codeBook, PyramidDenseSIFT<FImage> pdsift) {
        this.codeBook = codeBook;
        this.pdsift = pdsift;
    }

    @Override
    public DoubleFV extractFeature(FImage image) {
        pdsift.analyseImage(image); //Compute the dense sift descriptors.
        BagOfVisualWords<float[]> bovw = new BagOfVisualWords<float[]>(codeBook.getAssigner());

        BlockSpatialAggregator<float[], SparseIntFV> spatial = new BlockSpatialAggregator<float[], SparseIntFV>(bovw, 2, 2);

        return spatial.aggregate(pdsift.getFloatKeypoints(), image.getBounds()).normaliseFV();

//        float[][] imageDescriptors = Utilities.getDenseSIFT(image,pdsift);
//        double[] bagOfVisualWordHistogram = new double[codeBook.size()];
//        int representativeVectorIndex;
//
//        for (float[] imageDescriptor : imageDescriptors){
//            representativeVectorIndex = codeBook.getRepresentativeVectorIndexFromDescriptor(imageDescriptor);
//            bagOfVisualWordHistogram[representativeVectorIndex] +=1;
//        }
//
//        return new DoubleFV(bagOfVisualWordHistogram);

    }
}
