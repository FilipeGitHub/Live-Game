import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class LiveGame extends JPanel implements ActionListener, MouseMotionListener {
    private final JLayeredPane layeredPane;

    int gridSize = 5;
    Color[][] nextGeneration;
    Color[][]  currentColor;
    public LiveGame(){


        //inny kod
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.addMouseMotionListener(this);
        layeredPane.setLayout(new GridLayout(gridSize, gridSize));

        for(int i  = 0; i < Math.pow(gridSize,2); i++){
            JLabel label = createNewLabel(i);
            layeredPane.add(label);
        }
        add(Box.createRigidArea(new Dimension(0,10)));
        add(layeredPane);
        int delay = 2000; //milliseconds
        ActionListener taskPerformer = event -> {
            optainNextGeneraion();
            updatelayeredPane();
        };
        new Timer(delay, taskPerformer).start();


    }
    //funkcja ma za zadanie przypisanie wyznaczonych kolorów do instejącego układu
    private void updatelayeredPane() {
        for(int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                layeredPane.getComponent(i*gridSize + j).setBackground(nextGeneration[i][j]);
            }
        }

    }

    private JLabel createNewLabel(int id) {
        JLabel label = new JLabel(Integer.toString(id));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setOpaque(true);
        if(Math.random()>0.5) {
            label.setBackground(Color.BLACK);
        }
        else{
            label.setBackground(Color.LIGHT_GRAY);
        }
        label.setForeground(Color.GREEN);
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        label.setPreferredSize(new Dimension(300/gridSize,300/gridSize));
        return label;
    }


    private void optainNextGeneraion(){

        int x, y;
        currentColor= new Color[gridSize][gridSize];
        nextGeneration = new Color[gridSize][gridSize];

        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j ++){
                currentColor[i][j] = layeredPane.getComponent(i*gridSize + j).getBackground();
            }
        }
        int neighbours;
        Color eixaminedCell;
        Color [][] componentLocalArry;
        for(int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                neighbours = 0;
                eixaminedCell = currentColor[i][j];
                //System.out.println("i: " + i + "; j: " + j);
                componentLocalArry = slice(currentColor, new Dimension(i,j));
                for(int k = 0; k < componentLocalArry.length; k ++){
                    for(int l = 0; l < componentLocalArry[0].length; l ++){
                        if(componentLocalArry[k][l] == Color.LIGHT_GRAY){
                            neighbours++;
                        }
                    }
                }
                //Alive or die
                if(eixaminedCell == Color.LIGHT_GRAY){
                    neighbours--;
                    if(neighbours == 2 || neighbours == 3){
                        nextGeneration[i][j] = Color.LIGHT_GRAY;
                    }else{
                        nextGeneration[i][j] = Color.BLACK;
                    }
                }else{
                    if(neighbours == 3){
                        nextGeneration[i][j] = Color.LIGHT_GRAY;
                    }else{
                        nextGeneration[i][j] = Color.BLACK ;
                    }
                }
            }
        }
    }



    //function take point [0,0] in array as [1,1]
    //and [8,8] instead [7,7] in array
    private static Color[][] slice(Color[][] matrix, Dimension centralPoint) {

        Integer y = (int) centralPoint.getHeight();
        Integer x = (int) centralPoint.getWidth();
        Integer matrixY = matrix.length - 2;
        Integer matrixX = matrix[0].length - 2;
        Integer resultMatrixY = 3 , resultMatrixX = 3;

        if (!x.equals(0)) {
            x--;
        } else {
            resultMatrixX = 2;
        }
        if (x.equals(matrixX)) {
            resultMatrixX = 2;
        }

        if (!y.equals(0)) {
            y--;
        } else {
            resultMatrixY = 2;
        }
        if (y.equals(matrixY)) {
            resultMatrixY = 2;
        }
        Color result[][] = new Color[resultMatrixX][resultMatrixY];

        try {
            for (int i = 0; i < resultMatrixX; i++) {
                for (int j = 0; j < resultMatrixY; j++) {
                    result[i][j] = matrix[x + i][y + j];
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return result;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Live Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContetPale = new LiveGame();
        newContetPale.setOpaque(true);
        frame.setContentPane(newContetPale);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
