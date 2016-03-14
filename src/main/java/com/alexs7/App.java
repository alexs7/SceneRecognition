package com.alexs7;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import java.io.IOException;
import java.util.Map;

/**
 * Created by alex on 08/03/2016.
 */
public class App {

    public static void main(String[] args) throws IOException {
        ImageLoader imageLoader = new ImageLoader("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/SceneRecognition/training/");
        VFSGroupDataset<FImage> imagesMap = imageLoader.run();

        for (Map.Entry<String, VFSListDataset<FImage>> entry : imagesMap.entrySet()) {
            DisplayUtilities.display(entry.getKey(), entry.getValue());
        }
    }

}
