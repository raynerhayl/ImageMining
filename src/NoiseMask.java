import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Color;

/**
 * Created by raynerhayl on 9/08/16.
 */
public class NoiseMask extends Filter {

    private boolean median = true;

    public NoiseMask() {
      /*  super(new Mask[]{new Mask(new int[][]{new int[]{0, -1, 0},
                                              new int[]{-1, 5, -1},
                                              new int[]{0,-1, 0}}),

        });*/

        super(new Mask[]{new Mask(new double[][]{new double[]{1.0, 1.0, 1.0},
                new double[]{1.0, 1.0, 1.0},
                new double[]{1.0, 1.0, 1.0}}),

        });

        /*super(new Mask[]{new Mask(new int[][]{new int[]{1, -2, 1},
                new int[]{-2, 5, -2},
                new int[]{1,-2, 1}}),

        });*/


    }

    @Override
    public void apply(BufferedImage img) {
        Color[][] copy = Helper.copy(img);
        for (int col = 1; col < img.getWidth() - 1; col++) {
            for (int row = 1; row < img.getHeight() - 1; row++) {

                applyMasks(img,copy, col, row);

            }
        }

        Helper.copyBack(copy, img);
    }

    @Override
    void  applyMasks(BufferedImage img,Color[][]copy, int col, int row) {
        if(median) {
            medianMask(img, copy, col, row);
        } else {
            meanMask(img, copy, col, row);
        }
    }

    private void medianMask(BufferedImage img,Color[][] copy, int col, int row) {

        int[] redNums = new int[]{copy[col - 1][row - 1].getRed(), copy[col][row - 1].getRed(), copy[col + 1][row - 1].getRed(),
                copy[col - 1][row].getRed(), copy[col][row].getRed(), copy[col + 1][row].getRed(),
                copy[col - 1][row + 1].getRed(), copy[col][row + 1].getRed(), copy[col + 1][row + 1].getRed()};

        int[] greenNums = new int[]{copy[col - 1][row - 1].getGreen(), copy[col][row - 1].getGreen(), copy[col + 1][row - 1].getGreen(),
                copy[col - 1][row].getGreen(), copy[col][row].getGreen(), copy[col + 1][row].getGreen(),
                copy[col - 1][row + 1].getGreen(), copy[col][row + 1].getGreen(), copy[col + 1][row + 1].getGreen()};

        int[] blueNums = new int[]{copy[col - 1][row - 1].getBlue(), copy[col][row - 1].getBlue(), copy[col + 1][row - 1].getBlue(),
                copy[col - 1][row].getBlue(), copy[col][row].getBlue(), copy[col + 1][row].getBlue(),
                copy[col - 1][row + 1].getBlue(), copy[col][row + 1].getBlue(), copy[col + 1][row + 1].getBlue()};

        int[] rgbNums = new int[]{copy[col - 1][row - 1].getRGB(), copy[col][row - 1].getRGB(), copy[col + 1][row - 1].getRGB(),
                copy[col - 1][row].getRGB(), copy[col][row].getRGB(), copy[col + 1][row].getRGB(),
                copy[col - 1][row + 1].getRGB(), copy[col][row + 1].getRGB(), copy[col + 1][row + 1].getRGB()};

        int medianRed = median(redNums);
        int medianGreen = median(greenNums);
        int medianBlue = median(blueNums);

        //return median(rgbNums);

        int val = new Color(medianRed,medianGreen,medianBlue).getRGB();
        //img.setRGB(col, row, val);
        copy[col][row] = new Color(val);
    }

    private void meanMask(BufferedImage img, Color[][] copy, int col, int row) {

        double redTotal = copy[col - 1][row - 1].getRed() + copy[col][row - 1].getRed() + copy[col + 1][row - 1].getRed() +
                copy[col - 1][row].getRed() + copy[col][row].getRed() + copy[col + 1][row].getRed() +
                copy[col - 1][row + 1].getRed() + copy[col][row + 1].getRed() + copy[col + 1][row + 1].getRed();

        double greenTotal = copy[col - 1][row - 1].getGreen() + copy[col][row - 1].getGreen() + copy[col + 1][row - 1].getGreen() +
                copy[col - 1][row].getGreen() + copy[col][row].getGreen() + copy[col + 1][row].getGreen() +
                copy[col - 1][row + 1].getGreen() + copy[col][row + 1].getGreen() + copy[col + 1][row + 1].getGreen();

        double blueTotal = copy[col - 1][row - 1].getBlue() + copy[col][row - 1].getBlue() + copy[col + 1][row - 1].getBlue() +
                copy[col - 1][row].getBlue() + copy[col][row].getBlue() + copy[col + 1][row].getBlue() +
                copy[col - 1][row + 1].getBlue() + copy[col][row + 1].getBlue() + copy[col + 1][row + 1].getBlue();


        Color val = new Color((int)(redTotal / 9.0),(int)(greenTotal / 9.0),(int)(blueTotal / 9.0));
        copy[col][row] = val;
        img.setRGB(col,row,val.getRGB());
    }

    private int median(int[] nums) {
        List<Integer> oNums = new ArrayList<Integer>();
        int count = nums.length;


        while (count > 0) {
            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != Integer.MAX_VALUE && nums[i] <= min) {
                    min = nums[i];
                    minIndex = i;
                }
            }
            count--;
            oNums.add(min);
            nums[minIndex] = Integer.MAX_VALUE;
        }

        return oNums.get(oNums.size() / 2);
    }

    public void median(){
        median = true;
    }

    public void mean(){
        median = false;
    }
}
