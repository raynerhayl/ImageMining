
import ecs100.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PackedColorModel;
import java.io.File;
import java.io.IOException;

/**
 * Created by Haylem on 5/08/2016.
 */


public class Main {

    private final int LEFT = 20;
    private final int TOP = 20;

    private Galaxy galaxy = new Galaxy();

    public Main() {
        UI.initialise();
        UI.addButton("Edge Detection", this::edgeDetection);
        UI.addButton("Noise Removal", this::noiseRemoval);
        UI.addButton("DeBlur", this::deBlur);
        UI.addButton("Galaxy", this::galaxy);
        UI.addSlider("Threshold Strength",0,255,125,(double v)->{
            galaxy.setThreshold((int)v);
            if(galaxy.isImgSet()){

                galaxy.apply();
                galaxy.draw(LEFT, TOP);
            }
        });
    }


    public void galaxy(){
        UI.clearGraphics();
        UI.clearText();
        String fileName = "hubble.tif";
        UI.println("Loading image: "+fileName);
        BufferedImage img = loadImage(fileName);



        if(img == null){
            UI.println("Failed to load image, please try again.");
        } else {
            printDivide();
            UI.println("Displaying un-edited image.");
            UI.drawImage(img, LEFT,TOP);

            if(UI.askBoolean("Run blur Y/N")){
                LaplaceFilter laplaceFilter = new LaplaceFilter();
                laplaceFilter.blur();

                laplaceFilter.apply(img);

                galaxy.setImg(img);

                UI.println("Displaying blured image.");
                UI.drawImage(img, LEFT,TOP);

                if(UI.askBoolean("Run threshold Y/N")){
                    UI.println("Running threshold action.");
                    galaxy.apply(img);

                    UI.drawImage(img, LEFT,TOP);

                    UI.println("Adjust slider to change threshold,\n image will redraw automatically.");
                }
            }
        }

    }

    public void deBlur(){
        UI.clearGraphics();
        String fileName = "blurry-moon.tif";
        UI.println("Loading image: " + fileName);
        BufferedImage img = loadImage(fileName);
        if (img == null) {
            UI.println("Failed to load image, please try again.");
        } else {
            printDivide();
            UI.println("Displaying un-edited image");
            UI.drawImage(img, LEFT, TOP);

            boolean edit = UI.askBoolean("Run deBlur Y/N?");
            if (edit) {
                LaplaceFilter laplace = new LaplaceFilter();
                laplace.apply(img);
                UI.println("Displaying edited image.");
                UI.drawImage(img, LEFT, TOP);
            } else {
                return;
            }
        }
    }


    public void noiseRemoval() {
        UI.clearText();
        UI.clearGraphics();
        String fileName = "ckt-board-saltpep.tif";
        //String fileName = "fce5.gif";

        UI.println("Loading image: " + fileName);

        BufferedImage img = loadImage(fileName);
        if (img == null) {
            UI.println("Failed to load image, please try again.");
        } else {
            printDivide();
            UI.println("Displaying un-edited image");
            UI.drawImage(img, LEFT, TOP);

            boolean edit = UI.askBoolean("Run noise detection Y/N");

                if (edit) {
                    NoiseMask noise = new NoiseMask();

                    printDivide();
                    boolean median = UI.askBoolean("Median Method (Y) Mean Method (N)");
                    if (median) {
                        noise.median();

                        UI.println("Cancelling noise using median method.");
                        noise.apply(img);

                    } else {
                        noise.mean();

                        UI.println("Cancelling noise using mean method.");
                        noise.apply(img);

                    }

                    UI.println("Displaying edited image.");
                    UI.drawImage(img, LEFT, TOP);

            }
        }
    }

    public void edgeDetection() {
        UI.clearGraphics();
        String fileName = "test-pattern.tif";
        UI.println("Loading image: " + fileName);
        BufferedImage img = loadImage(fileName);
        if (img == null) {
            UI.println("Failed to load image, please try again.");
        } else {
            printDivide();
            UI.println("Displaying un-edited image");
            UI.drawImage(img, LEFT, TOP);

            boolean edit = UI.askBoolean("Run edge detection Y/N?");
            if (edit) {
                SobleFilter sobel = new SobleFilter();
                sobel.apply(img);
                UI.println("Displaying edited image.");
                UI.drawImage(img, LEFT, TOP);
            } else {
                return;
            }
        }
    }

    public void printDivide() {
        UI.println("---------------");
    }


    public BufferedImage loadImage(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(fileName));

            return image;
        } catch (IOException e) {

        }
        return null;
    }

    public void scale(int[] values, int scaler) {
        values[0] = values[0] * scaler;
        values[1] = values[1] * scaler;
        values[2] = values[2] * scaler;
    }

    public static void main(String[] args) {
        new Main();
    }
}
