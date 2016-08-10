import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Haylem on 5/08/2016.
 */
public class SobleFilter extends Filter {


    @Override
    public void apply(BufferedImage img) {
        Color[][] copy = Helper.copy(img);
        for (int col = 1; col < img.getWidth() - 1; col++) {
            for (int row = 1; row < img.getHeight() - 1; row++) {

                applyMasks(img,copy, col, row);

            }
        }


    }

    public SobleFilter() {

        super(new Mask[]{new Mask(new double[][]{new double[]{-1, 0, 1},
                new double[]{-2, 0, 2},
                new double[]{-1, 0, 1}}),

                new Mask(new double[][]{new double[]{-1, -2, -1},
                        new double[]{0, 0, 0},
                        new double[]{1, 2, 1}})});

    }

    @Override
    void applyMasks(BufferedImage img, Color[][] copy, int col, int row) {
        int total = 0;

        for (Mask m : this.masks) {
            total += Math.pow((m.getM()[0][0] * copy[col - 1][row - 1].getRGB()) + (m.getM()[0][1] * copy[col][row - 1].getRGB()) + (m.getM()[0][2] * copy[col + 1][row - 1].getRGB()) +
                    (m.getM()[1][0] * copy[col - 1][row].getRGB()) + (m.getM()[1][1] * copy[col][row].getRGB()) + (m.getM()[1][2] * copy[col + 1][row].getRGB()) +
                    (m.getM()[2][0] * copy[col - 1][row + 1].getRGB()) + (m.getM()[2][1] * copy[col][row + 1].getRGB()) + (m.getM()[2][2] * copy[col + 1][row + 1].getRGB()), 2);
        }

        int val = (int) Math.sqrt(total);
        img.setRGB(col, row, val);


    }
}
