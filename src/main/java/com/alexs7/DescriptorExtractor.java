package com.alexs7;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

/**
 * Created by alex on 06/04/2016.
 */
public class DescriptorExtractor implements FeatureExtractor<DoubleFV, FImage> {

    private CodeBook codeBook;

    public DescriptorExtractor(CodeBook codeBook) {
        this.codeBook = codeBook;
    }

    @Override
    public DoubleFV extractFeature(FImage image) {
        return Utilities.getFixedLengthVectorWordOccurences(codeBook, image);
    }
}
