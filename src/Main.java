
import ecs100.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Haylem on 5/08/2016.
 */



public class Main {
    public Main() {
        UI.initialise();
        loadImage();
    }

    public void loadImage(){
        try {
            // retrieve image
            BufferedImage image = ImageIO.read(new File("test-pattern.tif"));
            UI.drawImage(image, 0,0,image.getWidth(),image.getHeight());

            Mask sobel = new Mask(new int[][]{{1,1,1},{1,1,1},{1,1,1}});

            for(int col = 0; col < image.getWidth()-3; col++){
                for(int row = 0; row < image.getHeight()-3; row++){
                    sobel.apply(image);
                    sobel.setPos(col,row);
                }
            }
            UI.drawImage(image, 0,0,image.getWidth(),image.getHeight());


        } catch (IOException e) {

        }
    }

    public void scale (int[] values, int scaler){
        values[0] = values[0] * scaler;
        values[1] = values[1] * scaler;
        values[2] = values[2] * scaler;
    }

    public static void main(String[] args){
        new Main();
    }
}
