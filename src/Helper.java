import java.awt.*;

/**
 * Created by Haylem on 5/08/2016.
 */
public class Helper {

    public static int[] expandRGB(int pixel) {

        Color c = new Color(pixel);

        return new int[]{c.getRed(),c.getGreen(),c.getBlue()};
    }

    public static int contractRGB(int[] rgb){
        return new Color(rgb[0],rgb[1],rgb[2]).getRGB();
    }


}
