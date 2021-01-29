import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Pathfinding {

    public int nodes = 20;
    private int delay = 30;
    //private int gwidth = 20;
    private int startx = 9;
    private int starty = 9;
    private int finishx = 9;
    private int finishy = 9;
    private int curAlg = 0;
    Algorithm Alg = new Algorithm();
    private final int WIDTH = 850;
    private final int HEIGHT = 650;
    public int gSize = 600;
    private int nSize = gSize/nodes;
    private boolean solving = false;
    JFrame frame;
    JPanel toolP = new JPanel();
    JLabel algL = new JLabel("Algorithms:");
    private String[] algorithms = {"Dijkstra","A*"};
    JButton searchB = new JButton("Start Search");
    JButton startB = new JButton("Start Node");
    JComboBox algorithmsBx = new JComboBox(algorithms);
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

        toolP.setLayout(null);
        toolP.setBounds(10, 10, 210, 600);
        searchB.setBounds(40, 25, 120, 25);
        toolP.add(searchB);

        algL.setBounds(40, 75,120,25);
        toolP.add(algL);


        algorithmsBx.setBounds(40, 100, 120, 25);
        toolP.add(algorithmsBx);

        frame.getContentPane().add(toolP);

        canvas = new Grid();
        canvas.setBounds(230, 10, gSize+1, gSize+1);
        frame.getContentPane().add(canvas);

        searchB.addActionListener(new ActionListener(){        //ACTION LISTENERS
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        algorithmsBx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                curAlg = algorithmsBx.getSelectedIndex();
               update();
            }
        });
    }

    public void startSearch(){
        if(solving){
            switch(curAlg) {
                case 0:
                    Alg.Dijkstra();
            }
        }
        pause();
    }

    public void pause() {	//PAUSE STATE
        int i = 0;
        while(!solving) {
            i++;
            if(i > 500)
                i = 0;
            try {
                Thread.sleep(1);
            } catch(Exception e) {}
        }
        startSearch();	//START STATE
    }

    public void update(){
        canvas.repaint();
    }

    public void clearMap() {
        startx = 1;
        starty = 9;
        finishx = 18;
        finishy = 9;
        grid = new Node[nodes][nodes];
        for (int x = 0; x < nodes; x++) {
            for (int y = 0; y < nodes; y++) {
                grid[x][y] = new Node(3, x, y);
            }
        }
        grid[startx][starty] = new Node(0, startx, starty);
        grid[finishx][finishy] = new Node(1, finishx, finishy);
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
                        // 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
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

    public class Node {
        private int nType;
        private int lastX;
        private int lastY;
        private int x;
        private int y;


        public Node(int type, int x, int y) {
            nType = type;
            this.x = x;
            this.y = y;
        }

        public int getX() {return x;}		//GET METHODS
        public int getY() {return y;}
        public int getLastX() {return lastX;}
        public int getLastY() {return lastY;}
        public int getType() {return nType;}

        public void setType(int type) {nType = type;}		//SET METHODS
        public void setLastNode(int x, int y) {lastX = x; lastY = y;}
    }


    public class Algorithm {
        public void Dijkstra(){
            ArrayList<Node> priority = new ArrayList<Node>();

        }
    }





}

