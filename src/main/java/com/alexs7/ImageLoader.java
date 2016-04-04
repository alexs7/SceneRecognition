package com.alexs7;

import org.apache.commons.vfs2.FileSystemException;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by ar1v13 on 09/03/16.
 */
public class ImageLoader {

    String dir;

    public ImageLoader(String dir) {
        this.dir = dir;
    }

    public VFSGroupDataset<FImage> getGroupDataSet() {
        VFSGroupDataset<FImage> images =
                null;
        try {
            images = new VFSGroupDataset<>(dir, ImageUtilities.FIMAGE_READER);
        } catch (FileSystemException e) {
            e.printStackTrace();
        }

        return images;
    }

    public TreeMap<String, FImage> getMapDataSet() {
        Comparator<String> byImageFileName = (n1, n2) -> Integer.compare(Integer.parseInt(n1.split("\\.")[0]), Integer.parseInt(n2.split("\\.")[0]));
        TreeMap<String, FImage> images = new TreeMap<>(byImageFileName);

        final File file = new File(dir);
        for(final File imageFile : file.listFiles()) {
            try {
                images.put(imageFile.getName(), ImageUtilities.readF(imageFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return images;
    }
}
