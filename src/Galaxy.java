import ecs100.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Haylem on 10/08/2016.
 */
public class Galaxy {

    Map<Integer, Integer> histo = new HashMap<Integer, Integer>();
    private Color[][] copy;
    private BufferedImage img;
    private int threshold = 125;

    private boolean imgSet = false;

    public void setImg(BufferedImage img) {
        copy = Helper.copy(img);
    }

    public void setThreshold(int t) {
        threshold = t;
    }

    public void apply(){
        UI.println("Running threshold: "+threshold);
        apply(this.img);
    }

    public boolean isImgSet(){
        return imgSet;
    }

    public void apply(BufferedImage img){
        imgSet = true;
        this.img = img;
        for(int col = 0; col < img.getWidth(); col++){
            for(int row = 0; row < img.getHeight(); row++){
                if(copy[col][row].getRed() < threshold){
                    img.setRGB(col,row,new Color(0,0,0).getRGB());
                } else {
                    img.setRGB(col,row,new Color(255,255,255).getRGB());
                }
            }
        }
    }

    public void draw(int l, int t){
        UI.drawImage(img, l,t);
    }

    public void histo(BufferedImage img) {
        for (int col = 0; col < img.getWidth(); col++) {
            for (int row = 0; row < img.getHeight(); row++) {
                int intensity = new Color(img.getRGB(col, row)).getRed();
                if (histo.keySet().contains(intensity)) {
                    histo.put(new Integer(intensity), (Integer) new Integer(histo.get(intensity) + 1));
                } else {
                    histo.put(new Integer(intensity), 1);
                }
            }
        }
        try {

            Writer scan = new BufferedWriter(new FileWriter("test.txt"));
            for (Integer key : histo.keySet()) {
                scan.write(key + " " + histo.get(key) + "\n");
                scan.flush();
            }
        } catch (IOException e) {

        }
    }

}
