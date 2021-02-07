import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;

public class Pathfinding {

    public int nodes = 20;
    private int delay = 30;
    //private int gwidth = 20;
    private int lastX;
    private int lastY;
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
    JButton resetB = new JButton("Reset");
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

        toolP.setLayout(null);
        toolP.setBounds(10, 10, 210, 600);

        searchB.setBounds(40, 25, 120, 25);
        toolP.add(searchB);

        resetB.setBounds(40,65, 120, 25);
        toolP.add(resetB);

        algL.setBounds(40, 100,120,25);
        toolP.add(algL);

        algorithmsBx.setBounds(40, 125, 120, 25);
        toolP.add(algorithmsBx);

        frame.getContentPane().add(toolP);

        canvas = new Grid();
        canvas.setBounds(230, 10, gSize+1, gSize+1);
        frame.getContentPane().add(canvas);

        searchB.addActionListener(new ActionListener(){        //ACTION LISTENERS
            @Override
            public void actionPerformed(ActionEvent e) {
                solving=false;

                reset();
                update();
                try {
                    Thread.sleep(40);
                } catch(Exception g) {}
                solving = true;
            }
        });

        resetB.addActionListener(new ActionListener(){        //ACTION LISTENERS
            @Override
            public void actionPerformed(ActionEvent e) {
                solving = false;
                clearMap();
                update();
            }
        });

        algorithmsBx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                curAlg = algorithmsBx.getSelectedIndex();
                update();
            }
        });
        startSearch();
    }

    public void startSearch(){
        if(solving){
            switch(curAlg) {
                case 0:
                    Alg.Dijkstra();
                case 1:
                    Alg.aStar();
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

    public void delay() {	//DELAY METHOD
        try {
            Thread.sleep(delay);
        } catch(Exception e) {}
    }

    public void reset(){
//        grid = new Node[nodes][nodes];
        for (int x = 0; x < nodes; x++) {
            for (int y = 0; y < nodes; y++) {
                if(grid[x][y].getType() != 2) {
                    grid[x][y] = new Node(3, x, y);
                }
            }
        }
        grid[startx][starty] = new Node(0, startx, starty);
        grid[finishx][finishy] = new Node(1, finishx, finishy);
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
        public void mouseDragged(MouseEvent e) {
            try {
                int mouseX = e.getX() / nSize;
                int mouseY = e.getY() / nSize;
                if ((mouseX > -1) && (mouseX < nodes) && (mouseY > -1) && (mouseY < nodes) && ((mouseX != lastX) || (mouseY != lastY))) {
                    if(grid[mouseX][mouseY].getType() == 3){
                        grid[mouseX][mouseY] = new Node(2, mouseX, mouseY);
                    }else if((grid[mouseX][mouseY].getType()) != 0 && (grid[mouseX][mouseY].getType() != 1)) {
                        grid[mouseX][mouseY] = new Node(3, mouseX, mouseY);
                    }
                    lastX = mouseX;
                    lastY = mouseY;
                }
                canvas.repaint();
            }catch(Exception z){}
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                int mouseX = e.getX() / nSize;
                int mouseY = e.getY() / nSize;

                if ((mouseX > -1) && (mouseX < nodes) && (mouseY > -1) && (mouseY < nodes)) {
                    if(grid[mouseX][mouseY].getType() == 3){
                        grid[mouseX][mouseY] = new Node(2, mouseX, mouseY);
                    }else if((grid[mouseX][mouseY].getType()) != 0 && (grid[mouseX][mouseY].getType() != 1)) {
                        grid[mouseX][mouseY] = new Node(3, mouseX, mouseY);
                    }
                    lastX = mouseX;
                    lastY = mouseY;
                }
                canvas.repaint();
            }catch(Exception z){}
        }

        @Override
        public void mouseReleased(MouseEvent e) {}
    }

    public class Node {
        private int nType;
        private int lastX;
        private int lastY;
        private int x;
        private int y;
        private int dist;


        public Node(int type, int x, int y) {
            nType = type;
            this.x = x;
            this.y = y;
            dist = 0;
        }

        public int getManhattanDist(){
            int a = Math.abs(finishx - x);
            int b = Math.abs(finishy - y);
            return (a + b) * 2;
        }

        public int getX() {return x;}		//GET METHODS
        public int getY() {return y;}
        public int getLastX() {return lastX;}
        public int getLastY() {return lastY;}
        public int getType() {return nType;}
        private int getDist() {return dist;}

        public void setType(int type) {nType = type;}		//SET METHODS
        public void setLastNode(int x, int y) {lastX = x; lastY = y;}
        public void setDist(int dist){ this.dist = dist;}
    }


    public class Algorithm {
        public void Dijkstra(){
            ArrayList<Node> progress = new ArrayList<Node>();
            progress.add((grid[startx][starty]));
            while(solving) {
                if (progress.size() <= 0) {
                    solving = false;
                    break;
                }

                int newDist = progress.get(0).getDist() + 1;
                ArrayList<Node> explored = exploreAdj(progress.get(0), newDist);
                if (explored.size() > 0) {
                    progress.remove(0);
                    progress.addAll(explored);
                    update();
                    delay();
                } else progress.remove(0);
            }
        }

        public void aStar(){
            PriorityQueue<Node> progress = new PriorityQueue<Node>(new AStarComparator());
            progress.add((grid[startx][starty]));
            while(solving) {
                if (progress.size() <= 0) {
                    solving = false;
                    break;
                }
                int newDist = progress.peek().getDist() + 1;
                ArrayList<Node> explored = exploreAdj(progress.peek(), newDist);
                if (explored.size() > 0) {
                    progress.poll();
                    progress.addAll(explored);
                    update();
                    delay();
                } else progress.poll();
            }
        }



        public ArrayList<Node> exploreAdj(Node current, int dist){
            //Must check four adjacent blocks
            ArrayList<Node> explored = new ArrayList<Node>();
            if(current.getX() - 1 > -1) {
                Node neighbor = grid[current.x - 1][current.getY()];
                if(neighbor.getType() != 0 && neighbor.getType() != 2 && neighbor.getDist() == 0) {
                    explore(neighbor, current.getX(), current.getY(), dist);
                    explored.add(neighbor);
                }
            }
            if(current.getX() + 1 < nodes) {
                Node neighbor = grid[current.getX() + 1][current.getY()];
                if (neighbor.getType() != 0 && neighbor.getType() != 2 && neighbor.getDist() == 0) {
                    explore(neighbor, current.getX(), current.getY(), dist);
                    explored.add(neighbor);
                }
            }
            if(current.getY() - 1 > -1) {
                Node neighbor = grid[current.getX()][current.getY() - 1];
                if(neighbor.getType() != 0 && neighbor.getType() != 2 && neighbor.getDist() == 0) {
                    explore(neighbor, current.getX(), current.getY(), dist);
                    explored.add(neighbor);
                }
            }
            if(current.getY() + 1 < nodes) {
                Node neighbor = grid[current.getX()][current.getY() + 1];
                if (neighbor.getType() != 0 && neighbor.getType() != 2 && neighbor.getDist() == 0) {
                    explore(neighbor, current.getX(), current.getY(), dist);
                    explored.add(neighbor);
                }
            }
            return explored;
        }

        public void explore(Node current, int lastx, int lasty, int dist){
            if(current.getType() != 1){
                current.setType(4);
                current.setLastNode(lastx,lasty);
                current.setDist(dist);

            }

            if (current.getType() == 1) {
                current.setLastNode(lastx,lasty);
                current.setDist(dist);
                finalPath(current);
            }
        }

        public void finalPath(Node current){
            int length = current.getDist();
            System.out.println(current.getDist());
            int lx = current.getLastX();
            int ly = current.getLastY();
            while(length > 1) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
                current = grid[lx][ly];
                current.setType(5);
                lx = current.getLastX();
                ly = current.getLastY();
                length--;
            }
            solving = false;
        }
    }

    class AStarComparator implements Comparator<Node> {
        public int compare(Node a, Node b){
            int compA = a.getDist() + a.getManhattanDist();
            int compB = b.getDist() + b.getManhattanDist();
            if(compA < compB){
                return -1;
            } else if(compA > compB){
                return 1;
            }
            return 0;
        }
    }

}

