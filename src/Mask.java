import java.awt.image.BufferedImage;

/**
 * Created by Haylem on 5/08/2016.
 */
public class Mask {

    private int x = 0;
    private int y = 0;

    private int[][] m;

    public Mask(int[][] m){
        this.m = m;
    }

    public void apply(BufferedImage img){
        for(int col = 0; col < m.length; col++){
            for(int row = 0; row < m[col].length; row++){
                int[] rgb = Helper.expandRGB(img.getRGB(x + col, y + col));
                rgb[0] = rgb[0] * m[col][row];
                rgb[1] = rgb[1] * m[col][row];
                rgb[2] = rgb[2] * m[col][row];
                img.setRGB(x+col,y+col, Helper.contractRGB(rgb));
            }
        }
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
