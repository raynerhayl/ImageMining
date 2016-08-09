import java.awt.*;

/**
 * Created by Haylem on 5/08/2016.
 */
public class SobleFilter extends Filter {


    public SobleFilter() {

        super(new Mask[]{new Mask(new int[][]{new int[]{-1, 0, 1},
                new int[]{-2, 0, 2},
                new int[]{-1, 0, 1}}),

                new Mask(new int[][]{new int[]{-1, -2, -1},
                        new int[]{0, 0, 0},
                        new int[]{1, 2, 1}})});

    }

    @Override
    int applyMasks(Color[][] copy, int col, int row) {
        int total = 0;

        for (Mask m : this.masks) {
            total += Math.pow((m.getM()[0][0] * copy[col - 1][row - 1].getRGB()) + (m.getM()[0][1] * copy[col][row - 1].getRGB()) + (m.getM()[0][2] * copy[col + 1][row - 1].getRGB()) +
                    (m.getM()[1][0] * copy[col - 1][row].getRGB()) + (m.getM()[1][1] * copy[col][row].getRGB()) + (m.getM()[1][2] * copy[col + 1][row].getRGB()) +
                    (m.getM()[2][0] * copy[col - 1][row + 1].getRGB()) + (m.getM()[2][1] * copy[col][row + 1].getRGB()) + (m.getM()[2][2] * copy[col + 1][row + 1].getRGB()), 2);
        }

        return (int) Math.sqrt(total);    }
}
