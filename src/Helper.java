/**
 * Created by Haylem on 5/08/2016.
 */
public class Helper {

    public static int[] expandRGB(int pixel) {

        int red = (pixel>>16)&0x0ff;
        int green=(pixel>>8) &0x0ff;
        int blue= (pixel)    &0x0ff;

        return new int[]{red,green,blue};
    }

    public static int contractRGB(int[] argb){
        return ((argb[0]&0x0ff)<<16)|((argb[1]&0x0ff)<<8)|(argb[2]&0x0ff);
    }


}
