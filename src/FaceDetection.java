/**
 * Created by Haylem on 12/08/2016.
 */

import ecs100.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.PackedColorModel;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FaceDetection {


    boolean addingRegions = false;
    Point clicked;


    double height;
    double width;

    List<Region> mRegions = new ArrayList<Region>();

    public FaceDetection() {
        UI.initialise();
        UI.addButton("Parse Training Set", this::parseSets);
        UI.addButton("Create Regions", this::createRegions);
        UI.setMouseListener(this::mouseAction);

    }

    public void mouseAction(String action, double x, double y){
        if(addingRegions){
            if(action.equals("pressed")){
                clicked = new Point((int)x, (int)y);
            } else if(action.equals("released")){
                UI.setColor(Color.red);
                Region nR = new Region(clicked.x/width,clicked.y/height,(x-clicked.x)/width,(y-clicked.y)/height);
                mRegions.add(nR);
                for(Region r: mRegions){
                    UI.drawRect(r.left*width,r.top*height,r.width*width,r.height*height);
                }
                UI.setColor(Color.black);
            }
        }
    }


    public void createRegions(){
        try {
            BufferedImage img = ImageIO.read(new File("./train/face/face00001.pgm"));
             width = img.getWidth()*10;
             height = img.getHeight()*10;

            addingRegions = true;

            mRegions = new ArrayList<Region>();

            UI.drawImage(img,0,0,width,height);

        } catch (IOException ex) {

        }
    }

    public List<BufferedImage> loadImageSet(String dir) {
        List<File> files = new ArrayList<File>();
        File faceDir = new File(dir);
        loadDirectory(files, faceDir);

        return loadImages(files);

    }

    public void parseSets() {
        UI.println("Loading training set...");

        List<BufferedImage> faceImages = loadImageSet("./train/face");
        List<BufferedImage> nonFaceImages = loadImageSet("./train/non-face");

        List<BufferedImage> testFaceImages = loadImageSet("./test/face");
        List<BufferedImage> testNonFaceImages = loadImageSet("./test/non-face");

        UI.println("Loading done");

        double width = faceImages.get(0).getWidth()*10;
        double height = faceImages.get(0).getHeight()*10;
        UI.drawImage(faceImages.get(0),0,0,width,height);

        // 65 c 35 e
        /*Region[] regions = new Region[]{new Region(0, 0, (int) (width * (4.0 / 10.0)), (int) (height * (1.0 / 3.0))),
                new Region((int) (width * (6.0 / 10.0)), 0, (int) (width * (4.0 / 10.0)), (int) (height * (1.0 / 3.0))),
                new Region((int) (width / 4.0), (int) (height * (2.0 / 3.0)), (int) (width / 2.0), (int) (height * (1.0 / 3.0))),
                new Region((int) (width * (4.0 / 10.0)), (int) (height * (1.0 / 8.0)), (int) (width * (2.0 / 10.0)), (int) (height * (13.0 / 24.0)))
        };*/

        Region[] regions = new Region[]{new Region(0, 0, (int) (width * (4.0 / 10.0)), (int) (height * (1.0 / 3.0))),
                new Region((int) (width * (6.0 / 10.0)), 0, (int) (width * (4.0 / 10.0)), (int) (height * (1.0 / 3.0))),
                new Region((int) (0), (int) (height * (2.0 / 3.0)), (int) (width), (int) (height * (1.0 / 3.0))),
                new Region((int) (width * (3.0 / 10.0)), (int) (height * (1.0 / 8.0)), (int) (width * (4.0 / 10.0)), (int) (height * (7.0/8.0)))
        };

        //(int) (height * (13.0 / 24.0)

        UI.setColor(Color.red);

        for(int r = 0; r < regions.length; r++){
            Region region = regions[r];
            UI.drawRect(region.left,region.top,region.width,region.height);
        }

        List<double[]> trainStats = new ArrayList<double[]>();
        List<double[]> testStats = new ArrayList<double[]>();

        for (int i = 0; i < Math.max(Math.max(testFaceImages.size(),testNonFaceImages.size()),Math.max(faceImages.size(), nonFaceImages.size())); i++) {
            if (i < faceImages.size()) {
                trainStats.add(parseImage(faceImages.get(i), 1));

            }

            if (i < nonFaceImages.size()) {
                trainStats.add(parseImage(nonFaceImages.get(i), 0));

            }


            if (i < testFaceImages.size()) {
                testStats.add(parseImage(testFaceImages.get(i),1));
            }
            if (i < testNonFaceImages.size()) {
                testStats.add(parseImage(testNonFaceImages.get(i), 0));
            }
        }

        printCsv(trainStats, "train.csv");
        printCsv(testStats, "test.csv");

        UI.println("Printed to csv files");

    }

    public void printCsv(List<double[]> stats, String filename) {
        try {

            Writer scan = new BufferedWriter(new FileWriter(filename));

            for(int i = 0; i < mRegions.size(); i++){
                scan.write(i+", ");
            }
            scan.write("class\n");
            scan.flush();

            for (double[] d : stats) {
                for (int i = 0; i < d.length - 1; i++) {
                        String s =  String.format("%03f, ",d[i]);
                        scan.write(s);
                }
                String className = "false";
                if (d[8] == Double.MIN_NORMAL) {
                    className = "?";
                } else if (d[8] > 0) {
                    className = "true";
                }
                scan.write(className + "\n");
                scan.flush();
            }

        } catch (IOException e) {

        }
    }

    public Region[] createRegions(double width, double height){
        Region[] regions = new Region[mRegions.size()];

        for(int i = 0; i < mRegions.size(); i++){
            Region r = mRegions.get(i);
            regions[i] = new Region(r.left*width,r.top*height,r.width*width,r.height*height);
        }
        return regions;
    }



    public double[] parseImage(BufferedImage img, double className) {
        double[] stats = new double[mRegions.size()+1];

        double width = img.getWidth();
        double height = img.getHeight();
        Region[] regions = createRegions(width,height);
        int statCount = 0;

        for (int regionIndex = 0; regionIndex < regions.length; regionIndex++) {
            Region region = regions[regionIndex];
            double[] pixels = new double[(int)region.width * (int)region.height];
            int pCount = 0;
            double total = 0;

            for (int col = (int)region.left; col < region.left + region.width; col++) {
                for (int row = (int)region.top; row < region.top + region.height; row++) {
                    double intensity = new Color(img.getRGB(col, row)).getRed();
                    pixels[pCount] = intensity;
                    total += intensity;
                }
            }
            double mean = total / pixels.length;

            stats[statCount] = mean;
            statCount++;

            double deviation = 0;
            for (pCount = 0; pCount < pixels.length; pCount++) {
                deviation += Math.pow(pixels[pCount] - mean, 2);
            }
            double standardDeviation = Math.sqrt(deviation / pixels.length);
            stats[statCount] = standardDeviation;
            statCount++;
        }

        if (className==1) {
            stats[8] = 1;
        } else if(className == 0) {
            stats[8] = 0;
        } else if(className == Double.MIN_NORMAL ){
            stats[8] = Double.MIN_NORMAL;
        }

        return stats;
    }

    public void loadDirectory(List<File> files, File file) {
        if (file.isFile()) {
            files.add(file);
        } else {
            File[] fileList = file.listFiles();
            for (int fileIndex = 0; fileIndex < fileList.length; fileIndex++) {
                loadDirectory(files, fileList[fileIndex]);
            }
        }
    }

    public List<BufferedImage> loadImages(List<File> files) {
        List<BufferedImage> images = new ArrayList<BufferedImage>();
        for (File f : files) {
            try {
                images.add(ImageIO.read(f));

            } catch (IOException ex) {

            }
        }
        return images;
    }


    class Region {
        double left;
        double top;
        double width;
        double height;

        public Region(double leftV, double topV, double widthV, double heightV) {
            left = leftV;
            top = topV;
            width = widthV;
            height = heightV;
        }
    }

    public static void main(String[] args) {
        new FaceDetection();
    }

}
