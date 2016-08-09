import java.util.*;
import java.awt.Color;

/**
 * Created by raynerhayl on 9/08/16.
 */
public class NoiseMask extends Filter {

    public NoiseMask() {
      /*  super(new Mask[]{new Mask(new int[][]{new int[]{0, -1, 0},
                                              new int[]{-1, 5, -1},
                                              new int[]{0,-1, 0}}),

        });*/

        super(new Mask[]{new Mask(new int[][]{new int[]{-1, -1, -1},
                new int[]{-1, 9, -1},
                new int[]{-1, -1, -1}}),

        });

        /*super(new Mask[]{new Mask(new int[][]{new int[]{1, -2, 1},
                new int[]{-2, 5, -2},
                new int[]{1,-2, 1}}),

        });*/


    }

    @Override
    int applyMasks(Color[][] copy, int col, int row) {
        return medianMask(copy, col, row);
    }

    private int medianMask(Color[][] copy, int col, int row) {

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
        return new Color(medianRed,medianGreen,medianBlue).getRGB();

    }

    private int normalMask(Color[][] copy, int col, int row) {
        int total = 0;

        for (Mask m : masks) {
            total += Math.pow((m.getM()[0][0] * copy[col - 1][row - 1].getRGB()) + (m.getM()[0][1] * copy[col][row - 1].getRGB()) + (m.getM()[0][2] * copy[col + 1][row - 1].getRGB()) +
                    (m.getM()[1][0] * copy[col - 1][row].getRGB()) + (m.getM()[1][1] * copy[col][row].getRGB()) + (m.getM()[1][2] * copy[col + 1][row].getRGB()) +
                    (m.getM()[2][0] * copy[col - 1][row + 1].getRGB()) + (m.getM()[2][1] * copy[col][row + 1].getRGB()) + (m.getM()[2][2] * copy[col + 1][row + 1].getRGB()), 2);
        }

        return (int) Math.sqrt(total);
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
}
