import java.awt.*;
import java.net.Inet4Address;
import java.util.ArrayList;

public class NextGeneration {
    private static ArrayList<Color> nextGenerationArray;
    private static Integer sizeX, sizeY;
    private static ArrayList <Color> currentlyTestedPoint;
    private static ArrayList <Color> currentColorArray;

    private static Dimension dim;
    private static Double id;

    NextGeneration(Integer sizeX, Integer sizeY){
        nextGenerationArray = new ArrayList <>();
        currentlyTestedPoint = new ArrayList<>();
        currentColorArray = new ArrayList<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    static void generate(Component[] components){
        Color currentColor;
        nextGenerationArray.clear();
        currentColorArray.clear();
        for(Component c: components){
            currentColorArray.add(c.getBackground());
        }
        int neighbours;
        for(int i = 0; i < sizeX*sizeY; i++) {
            neighbours = 0;
            currentColor = currentColorArray.get(i);
            slice(currentColorArray, getDimention(i));
            for (Color c : currentlyTestedPoint) {
                if (c.equals(Color.LIGHT_GRAY)) {
                    neighbours++;
                }
            }
            if (currentColor == Color.LIGHT_GRAY) {
                neighbours--;
                if (neighbours == 2 || neighbours == 3) {
                    nextGenerationArray.add(Color.LIGHT_GRAY);
                } else {
                    nextGenerationArray.add(Color.BLACK);
                }
            }else{
                if(neighbours == 3){
                    nextGenerationArray.add(Color.LIGHT_GRAY);
                }else{
                    nextGenerationArray.add(Color.BLACK);
                }
            }


        }
    }
    //function take point [0,0] in array as [1,1]
    //and [8,8] instead [7,7] in array
    private static void slice(ArrayList<Color> currentColorArrays, Dimension examinedPoint) {
        Integer examinedPointY = (int) examinedPoint.getHeight() - 1;
        Integer examinedPointX = (int) examinedPoint.getWidth() - 1;
        currentlyTestedPoint.clear();
        Integer  returnedDimensionY  = 3 ,  returnedDimensionX = 3;

        if (!examinedPointX.equals(0)) {
            examinedPointX--;
        } else {
            returnedDimensionX = 2;
        }
        if (examinedPointX.equals(sizeX-2)) {
            returnedDimensionX = 2;
        }

        if (!examinedPointY.equals(0)) {
            examinedPointY--;
        } else {
            returnedDimensionY  = 2;
        }
        if (examinedPointY.equals(sizeY-2)) {
            returnedDimensionY  = 2;
        }
        try {
            for (int i = 0; i <  returnedDimensionX; i++) {
                for (int j = 0; j <  returnedDimensionY ; j++) {
                    currentlyTestedPoint.add(currentColorArrays.get((j+examinedPointY)*sizeX + i + examinedPointX ));
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private static double getId(Double id) {
        return dim.getHeight()*sizeY + dim.getWidth();
    }

    private static Dimension getDimention(int i) {
        return new Dimension(i%sizeX+1, i/sizeY+1);
    }
    public static Color get(Integer i){
        return nextGenerationArray.get(i);
    }


}
