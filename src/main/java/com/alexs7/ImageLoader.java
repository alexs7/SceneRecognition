package com.alexs7;

import org.apache.commons.vfs2.FileSystemException;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

/**
 * Created by ar1v13 on 09/03/16.
 */
public class ImageLoader {

    String dir;

    public ImageLoader(String dir) {
        this.dir = dir;
    }

    public VFSGroupDataset<FImage> run() {
        VFSGroupDataset<FImage> images =
                null;
        try {
            images = new VFSGroupDataset<FImage>(dir, ImageUtilities.FIMAGE_READER);
        } catch (FileSystemException e) {
            e.printStackTrace();
        }

        return images;
    }
}
