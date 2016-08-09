/**
 * Created by Haylem on 5/08/2016.
 */
public class SobleFilter extends Filter {

    static int m1[][] = {{-1,0,1},
            {-2,0,2},
            {-1,0,1}};
    static int m2[][] = {{-1,-2,-1},
            {0,0,0},
            {1,2,1}};

    public SobleFilter(){

        super(m1,m2);

    }

}
