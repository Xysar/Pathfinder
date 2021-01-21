import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

public class Pathfinding {
    JFrame frame;
    private final int WIDTH = 850;
    private final int HEIGHT = 650;
    JButton searchB = new JButton("Start Search");
    JButton startB = new JButton("Start Node");
    Node[][] grid;
    public Pathfinding(){
        initialize();
    }

    public static void main(String[] args) {
        new Pathfinding();
    }

    public void initialize(){
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH,HEIGHT);
        frame.setTitle("Path Finding");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }

}

