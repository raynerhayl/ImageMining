import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;

/**
 * Created by raynerhayl on 9/08/16.
 */
public abstract class Filter {

    private int x = 0;
    private int y = 0;

    private int[][] m1;
    private int[][] m2;


    protected List<Mask> masks = new ArrayList<Mask>();

    private int[][] newImg;

    public Filter(Mask[] m) {
        for (int i = 0; i < m.length; i++) {
            masks.add(m[i]);
        }
    }


    public void apply(BufferedImage img) {

        Color[][] copy = Helper.copy(img);
        for (int col = 1; col < img.getWidth() - 1; col++) {
            for (int row = 1; row < img.getHeight() - 1; row++) {

                int val = applyMasks(copy, col, row);

                img.setRGB(col, row, val);
            }
        }
    }

    abstract int applyMasks(Color[][] copy, int col, int row);


    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
