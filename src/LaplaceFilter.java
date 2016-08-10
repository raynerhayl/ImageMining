import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Haylem on 10/08/2016.
 */
public class LaplaceFilter extends Filter {

    /**
     * Created by Haylem on 5/08/2016.
     */

    private boolean blur = false;

    public void blur(){
        blur = true;
    }

    public void shaerpen(){
        blur = false;
    }

    @Override
    public void apply(BufferedImage img) {
        Color[][] copy = Helper.copy(img);
        for (int col = 1; col < img.getWidth() - 1; col++) {
            for (int row = 1; row < img.getHeight() - 1; row++) {

                applyMasks(img, copy, col, row);

            }
        }

    }

    public LaplaceFilter() {

        super(new Mask[]{new Mask(new double[][]{
                new double[]{0, -1.0, 0},
                new double[]{-1.0, 5.0, -1.0},
                new double[]{0, -1.0, 0}}),

                new Mask(new double[][]{
                        new double[]{1.0/9.0,1.0/9.0, 1.0/9.0},
                        new double[]{1.0/9.0, 1.0/9.0, 1.0/9.0},
                        new double[]{1.0/9.0,1.0/9.0, 1.0/9.0}})
        });

    }

    /*@Override
    void applyMasks(BufferedImage img, Color[][] copy, int col, int row) {
        int total = 0;

        for (Mask m : this.masks) {
            total += ((m.getM()[0][0] * copy[col - 1][row - 1].getRGB()) + (m.getM()[0][1] * copy[col][row - 1].getRGB()) + (m.getM()[0][2] * copy[col + 1][row - 1].getRGB()) +
                    (m.getM()[1][0] * copy[col - 1][row].getRGB()) + (m.getM()[1][1] * copy[col][row].getRGB()) + (m.getM()[1][2] * copy[col + 1][row].getRGB()) +
                    (m.getM()[2][0] * copy[col - 1][row + 1].getRGB()) + (m.getM()[2][1] * copy[col][row + 1].getRGB()) + (m.getM()[2][2] * copy[col + 1][row + 1].getRGB()));
        }

        int val = (int)(total);
        img.setRGB(col, row, copy[col][row].getRGB() + val);
    }*/

    @Override
    void applyMasks(BufferedImage img, Color[][] copy, int col, int row) {
        Mask m;
        if(blur){
            m = masks.get(1);
        } else {
            m = masks.get(0);
        }

        double redTotal = ((m.getM()[0][0] * (double) copy[col - 1][row - 1].getRed()) + (m.getM()[0][1] * (double) copy[col][row - 1].getRed()) + (m.getM()[0][2] * (double) copy[col + 1][row - 1].getRed()) +
                (m.getM()[1][0] * (double) copy[col - 1][row].getRed()) + (m.getM()[1][1] * (double) copy[col][row].getRed()) + (m.getM()[1][2] * (double) copy[col + 1][row].getRed()) +
                (m.getM()[2][0] * (double) copy[col - 1][row + 1].getRed()) + (m.getM()[2][1] * (double) copy[col][row + 1].getRed()) + (m.getM()[2][2] * (double) copy[col + 1][row + 1].getRed()));

        double greenTotal = ((m.getM()[0][0] * (double) copy[col - 1][row - 1].getGreen()) + (m.getM()[0][1] * (double) copy[col][row - 1].getGreen()) + (m.getM()[0][2] * (double) copy[col + 1][row - 1].getGreen()) +
                (m.getM()[1][0] * (double) copy[col - 1][row].getGreen()) + (m.getM()[1][1] * (double) copy[col][row].getGreen()) + (m.getM()[1][2] * (double) copy[col + 1][row].getGreen()) +
                (m.getM()[2][0] * (double) copy[col - 1][row + 1].getGreen()) + (m.getM()[2][1] * (double) copy[col][row + 1].getGreen()) + (m.getM()[2][2] * (double) copy[col + 1][row + 1].getGreen()));

        double blueTotal = ((m.getM()[0][0] * (double) copy[col - 1][row - 1].getBlue()) + (m.getM()[0][1] * (double) copy[col][row - 1].getBlue()) + (m.getM()[0][2] * (double) copy[col + 1][row - 1].getBlue()) +
                (m.getM()[1][0] * (double) copy[col - 1][row].getBlue()) + (m.getM()[1][1] * (double) copy[col][row].getBlue()) + (m.getM()[1][2] * (double) copy[col + 1][row].getBlue()) +
                (m.getM()[2][0] * (double) copy[col - 1][row + 1].getBlue()) + (m.getM()[2][1] * (double) copy[col][row + 1].getBlue()) + (m.getM()[2][2] * (double) copy[col + 1][row + 1].getBlue()));


        redTotal = Math.min(255, Math.max(redTotal, 0));
        greenTotal = Math.min(255, Math.max(greenTotal, 0));
        blueTotal = Math.min(255, Math.max(blueTotal, 0));


        Color val = new Color((int) redTotal, (int) greenTotal, (int) blueTotal);
        //copy[col][row] = val;
        img.setRGB(col, row, val.getRGB());
    }
}



