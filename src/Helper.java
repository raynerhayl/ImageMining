import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Haylem on 5/08/2016.
 */
public class Helper {


    public static int[] expandRGB(int pixel) {

        Color c = new Color(pixel);

        return new int[]{c.getRed(),c.getGreen(),c.getBlue()};
    }

    public static Color[][] copy(BufferedImage img){
        Color[][] copy = new Color[img.getWidth()][img.getHeight()];
        for(int col = 0; col < img.getWidth(); col++){
            for(int row = 0; row < img.getHeight(); row++){
                copy[col][row] = new Color(img.getRGB(col,row));
            }
        }
        return copy;
    }

    public static int contractRGB(int[] rgb){
        return new Color(rgb[0],rgb[1],rgb[2]).getRGB();
    }


}
