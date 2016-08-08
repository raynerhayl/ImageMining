import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Haylem on 5/08/2016.
 */
public class Mask {

    private int x = 0;
    private int y = 0;

    private int[][] m;

    private int[][] newImg;

    int sobleX[][] = {{-1,0,1},
            {-2,0,2},
            {-1,0,1}};
    int sobleY[][] = {{-1,-2,-1},
            {0,0,0},
            {1,2,1}};

    public Mask(int[][] m){
        this.m = m;
    }

    public void apply(BufferedImage img){
        newImg = new int[img.getWidth()][img.getHeight()];
        Color[][] copy = Helper.copy(img);
        for(int col = 1; col < m.length; col++){
            for(int row = 1; row < m[col].length; row++){

                double pixel_x = (sobleX[0][0] * copy[col -1][row -1].getRGB()) + (sobleX[0][1] * copy[col][row -1].getRGB()) + (sobleX[0][2] * copy[col +1][row -1].getRGB()) +
                        (sobleX[1][0] * copy[col -1][row].getRGB())   + (sobleX[1][1] * copy[col][row].getRGB())   + (sobleX[1][2] * copy[col +1][row].getRGB()) +
                        (sobleX[2][0] * copy[col -1][row +1].getRGB()) + (sobleX[2][1] * copy[col][row +1].getRGB()) + (sobleX[2][2] * copy[col +1][row +1].getRGB());
                double pixel_y = (sobleY[0][0] * copy[col -1][row -1].getRGB()) + (sobleY[0][1] * copy[col][row -1].getRGB()) + (sobleY[0][2] * copy[col +1][row -1].getRGB()) +
                        (sobleY[1][0] * copy[col -1][row].getRGB())   + (sobleY[1][1] * copy[col][row].getRGB())   + (sobleY[1][2] * copy[col +1][row].getRGB()) +
                        (sobleY[2][0] * copy[col -1][row +1].getRGB()) + (sobleY[2][1] * copy[col][row +1].getRGB()) + (sobleX[2][2] * copy[col +1][row +1].getRGB());

                int val = (int)Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                if(val < 0)
                {
                    val = 0;
                }

                if(val > 255)
                {
                    val = 255;
                }

                val = 0;

                img.setRGB(col +col, row +col, val);
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
