import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class LiveGame extends JPanel implements ActionListener, MouseMotionListener {

    private final JLayeredPane layeredPane;
    private final Integer gridSize = 30;
    private static NextGeneration nextGeneration;
    private static Integer sizeX, sizeY;
    private static ArrayList <Color> currentlyTestedPoint;
    private static ArrayList <Color> currentColorArray;


    public LiveGame(){
        sizeX = gridSize;
        sizeY = gridSize;
        nextGeneration = new NextGeneration(sizeX,sizeY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(30*sizeX, 30*sizeY));
        layeredPane.addMouseMotionListener(this);
        layeredPane.setLayout(new GridLayout(gridSize, gridSize));

        for(int i  = 0; i < Math.pow(gridSize,2); i++){
            JLabel label = createNewLabel(i);
            layeredPane.add(label);
        }
        add(layeredPane);
        int delay = 2000; //milliseconds
        ActionListener taskPerformer = event -> {
            nextGeneration.generate(layeredPane.getComponents());
            updatelayeredPane();
        };
        new Timer(delay, taskPerformer).start();
    }
    static void createAndShowGUI(){
        JFrame frame = new JFrame("Live Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContetPale = new LiveGame();
        newContetPale.setOpaque(true);
        frame.setContentPane(newContetPale);

        frame.pack();
        frame.setVisible(true);
    }

    private void updatelayeredPane() {
        for(int i = 0; i < sizeX*sizeY; i++){
            layeredPane.getComponent(i).setBackground(nextGeneration.get(i));
        }
    }

    private JLabel createNewLabel(int id) {
        JLabel label = new JLabel();
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
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        label.setPreferredSize(new Dimension(30,30));
        return label;
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


}
