import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Created by raynerhayl on 9/08/16.
 */
public class Filter {

    private int x = 0;
    private int y = 0;

    private int[][] m1;
    private int[][] m2;

    static class Mask{
        int[][] m;

        public Mask(int[][] m){
            this.m = m;
        }
    }

    private List<Mask> masks = new ArrayList<Mask>();

    private int[][] newImg;

    public Filter(int[][] m1, int[][] m2){
        this.m1 = m1;
        this.m2 = m2;
    }

    public void apply(BufferedImage img){

        Color[][] copy = Helper.copy(img);
        for(int col = 1; col < img.getWidth()-1; col++){
            for(int row = 1; row < img.getHeight()-1; row++){

                double pixel_x = (m1[0][0] * copy[col -1][row -1].getRGB()) + (m1[0][1] * copy[col][row -1].getRGB()) + (m1[0][2] * copy[col +1][row -1].getRGB()) +
                        (m1[1][0] * copy[col -1][row].getRGB())   + (m1[1][1] * copy[col][row].getRGB())   + (m1[1][2] * copy[col +1][row].getRGB()) +
                        (m1[2][0] * copy[col -1][row +1].getRGB()) + (m1[2][1] * copy[col][row +1].getRGB()) + (m1[2][2] * copy[col +1][row +1].getRGB());
                double pixel_y = (m2[0][0] * copy[col -1][row -1].getRGB()) + (m2[0][1] * copy[col][row -1].getRGB()) + (m2[0][2] * copy[col +1][row -1].getRGB()) +
                        (m2[1][0] * copy[col -1][row].getRGB())   + (m2[1][1] * copy[col][row].getRGB())   + (m2[1][2] * copy[col +1][row].getRGB()) +
                        (m2[2][0] * copy[col -1][row +1].getRGB()) + (m2[2][1] * copy[col][row +1].getRGB()) + (m1[2][2] * copy[col +1][row +1].getRGB());

                int val = (int)Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                if(val < 0)
                {
                    val = 0;
                }

                img.setRGB(col, row, val);
            }
        }
    }



    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
