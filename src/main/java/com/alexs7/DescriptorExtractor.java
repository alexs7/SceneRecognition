package com.alexs7;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

import java.util.ArrayList;

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
        ArrayList<double[]> imageDescriptors = Utilities.getImageDescriptors(image);
        double[] bagOfVisualWordHistorgram = new double[codeBook.size()];
        int representativeVectorIndex;

        for (double[] imageDescriptor : imageDescriptors){
            representativeVectorIndex = codeBook.getRepresentativeVectorIndexFromDescriptor(imageDescriptor);
            bagOfVisualWordHistorgram[representativeVectorIndex] +=1;
        }

        return new DoubleFV(bagOfVisualWordHistorgram);
    }
}
