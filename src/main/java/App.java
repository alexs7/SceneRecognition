import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;

/**
 * Created by alex on 08/03/2016.
 */
public class App {

    public static void main(String[] args){
        MBFImage image = null;
        try {
            image = ImageUtilities.readMBF(new File("/Users/alex/Projects/University Notes/COMP6223 Computer Vision/CW3/training/Coast/0.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}