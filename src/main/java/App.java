import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import java.io.File;
import java.io.IOException;

/**
 * Created by alex on 08/03/2016.
 */
public class App {

    public static void main(String[] args){
        ImageLoader imageLoader = new ImageLoader();
        try {
            imageLoader.loadImagesFromDir("/home/ar1v13/Projects/UniWork/SceneRecognition/training/bedroom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
