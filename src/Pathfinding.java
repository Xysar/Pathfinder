import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Pathfinding {
    JFrame frame;
    JPanel toolP = new JPanel();
    public int nodes = 20;

    //private int gwidth = 20;
    private int startx = -1;
    private int starty = -1;
    private int finishx = -1;
    private int finishy = -1;
    private final int WIDTH = 850;
    private final int HEIGHT = 650;
    public int gSize = 600;
    private int nSize = gSize/nodes;
    JButton searchB = new JButton("Start Search");
    JButton startB = new JButton("Start Node");
    Node[][] grid; // 2D array that represents the nodes in the grid, each of which have a distance from start, and previous node
    Grid canvas; // GUI representation of the grid data structure
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    public Pathfinding() {
        clearMap();
        initialize();
    }

    public static void main(String[] args) {
        new Pathfinding();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Path Finding");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        toolP.setBorder(BorderFactory.createTitledBorder(loweredetched, "Controls"));
        int space = 25;
        int buff = 45;

        toolP.setLayout(null);
        toolP.setBounds(10, 10, 210, 600);
        searchB.setBounds(40, space, 120, 25);
        toolP.add(searchB);

        frame.getContentPane().add(toolP);

        canvas = new Grid();
        canvas.setBounds(230, 10, gSize+1, gSize+1);
        frame.getContentPane().add(canvas);

        searchB.addActionListener(new ActionListener(){        //ACTION LISTENERS
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void clearMap() {
        startx = -1;
        starty = -1;
        finishx = -1;
        finishy = -1;
        grid = new Node[nodes][nodes];
        for (int x = 0; x < nodes; x++) {
            for (int y = 0; y < nodes; y++) {
                grid[x][y] = new Node(3, x, y);
            }
        }
    }

    public void pause() {

    }

    public class Grid extends JPanel implements MouseListener, MouseMotionListener {
        public Grid() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paintComponent(Graphics g) {    //REPAINT
            super.paintComponent(g);
            for (int x = 0; x < nodes; x++) {    //PAINT EACH NODE IN THE GRID
                for (int y = 0; y < nodes; y++) {
                    switch (grid[x][y].getType()) {
                        case 0:
                            g.setColor(Color.GREEN);
                            break;
                        case 1:
                            g.setColor(Color.RED);
                            break;
                        case 2:
                            g.setColor(Color.BLACK);
                            break;
                        case 3:
                            g.setColor(Color.WHITE);
                            break;
                        case 4:
                            g.setColor(Color.CYAN);
                            break;
                        case 5:
                            g.setColor(Color.YELLOW);
                            break;
                    }
                    g.fillRect(x * nSize, y * nSize, nSize, nSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * nSize, y * nSize, nSize, nSize);
                }
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
}

