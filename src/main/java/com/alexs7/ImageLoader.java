package com.alexs7;

import org.apache.commons.io.FilenameUtils;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ar1v13 on 09/03/16.
 */
public class ImageLoader {

    public ImageLoader() {}

    public List<FImage> loadImagesFromDir(String dirValue) throws IOException {
        List<FImage> fImageList = new ArrayList<FImage>();
        File dir = new File(dirValue);
        File[] directoryListing = dir.listFiles();

        for (File file : directoryListing) {
            String ext = FilenameUtils.getExtension(file.getName());
            if(ext.equals("jpg")) {
                FImage fImage = ImageUtilities.readF(file);
                fImageList.add(fImage);
            }
        }

        return fImageList;
    }
}
