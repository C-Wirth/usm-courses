import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.*;

public class VisualFrame {

    public static final Color BACKGROUND_COLOR= Color.WHITE;
    public static final Color GRAPH_COLOR = Color.BLACK;
    public static final Color NODE_COLOR = Color.BLACK;
    public static final Color ORIGIN_NODE_COLOR = Color.GREEN;
    public static final Color DESTINATION_NODE_COLOR = Color.RED;
    public static final Color NODE_TRAVERSED = Color.YELLOW;
    
    public DrawingPanel panel;
    private JFrame frame;
    public int width;
    public int height;
    public int rows;
    public int columns;
    public LinkedList<Point2D> shortestPath;

    public VisualFrame(int width, int height,int columns, int rows){


        this.width = width;
        this.height = height;
        this.columns = columns;
        this.rows = rows;

        initializeFrame();
    }

    private void initializeFrame(){

        frame = new JFrame();
        panel = new DrawingPanel(width, height, rows, columns);
        frame.add(panel);

        frame.setTitle("Algorithm Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Assisted with methods by ChatGPT on  21 October 2024
     */
   class DrawingPanel extends JPanel {

        private int panelWidth;
        private int panelHeight;
        private int numRows;
        private int numCols;
        private Set<Point2D> nodesToDraw;

        public DrawingPanel(int width, int height, int rows, int columns) {
            this.panelWidth = width;
            this.panelHeight = height;
            this.numRows = rows;
            this.numCols = columns;
            setBackground(BACKGROUND_COLOR); 

            addMouseListener(new MouseAdapter() {
            
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    int col = (int) ((double) x / panelWidth * numCols); // Calculate column index
                    int row = (int) ((double) y / panelHeight * numRows); // Calculate row index
                   
                    Point2D node = new Point2D.Double(col, row);

                    try {
                        if (e.getButton() == MouseEvent.BUTTON1) { //left mouse click creates/deletes a node
                            main.gt.flipSquare(node);
                        } else if (e.getButton() == MouseEvent.BUTTON3) { //right mouse click designates origin/destination locations for traversal
                           main.gt.flipDesignation(node);
                        }

                    } catch (Graph.NodeAlreadyExists e1) {
                    }
                
                if(main.gt.origin != null && main.gt.destination != null){

                    long startTime = System.nanoTime();

                    System.out.println("Executing Searching Algorithm");

                    shortestPath = main.algorithm.equals("dijkstra") ? main.gt.graph.dijkstra(main.gt.origin, main.gt.destination) 
                                                                              : main.gt.graph.aStar(main.gt.origin, main.gt.destination);
                    
                    long elapsedTime = System.nanoTime() - startTime;

                     System.out.println("Total time for algorithm: " + elapsedTime/1000 + " milliseconds");
                                                                              
                  
                }
                repaint();   
                }
                
            });
            
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(main.numNodes == 1000000) //cant visualize big graphs
                return;
            
            drawRows(g);
            drawColumns(g);

            if (nodesToDraw != null) 
                drawNodes(g);
            
            if (!main.gt.searchedNodes.isEmpty()){
                    drawSearched(g);
                }
            
        }

        private void drawSearched(Graphics g) {

            for(Point2D node : main.gt.searchedNodes){

                g.setColor(Color.YELLOW);

                int x = (int) (node.getX() * ((panelWidth / columns)));
                int y = (int) (node.getY() * ((panelHeight / rows)));
                int w = (panelWidth / columns) - 1;
                int h = (panelHeight / rows) - 1;

                if (shortestPath.contains(node) || node.equals(main.gt.origin))
                    g.setColor(Color.GREEN);            
            
                g.fillRect(x, y, w, h);
            }
        }
        


        private void drawRows(Graphics g) {
            g.setColor(NODE_COLOR);
            int rowHeight = panelHeight / numRows;
            for (int i = 0; i <= numRows; i++) {
                int y = i * rowHeight;
                g.drawLine(0, y, panelWidth, y);
            }
        }

        private void drawColumns(Graphics g) {
            g.setColor(NODE_COLOR);
            int colWidth = panelWidth / numCols;
            for (int i = 0; i <= numCols; i++) {
                int x = i * colWidth;
                // Graphics2D g2d = (Graphics2D) g;
                // g2d.setStroke(new BasicStroke(10.0f));
                g.drawLine(x, 0, x, panelHeight);
            }
        }

        private void drawNodes(Graphics g) {

            for(Point2D node : nodesToDraw){

                int x = (int) (node.getX() * ((panelWidth / columns)));
                int y = (int) (node.getY() * ((panelHeight / rows)));
                int w = (panelWidth/columns)-1;
                int h = (panelHeight/rows)-1;
                
                g.setColor(main.gt.graph.grid.containsKey(node) ? NODE_COLOR : BACKGROUND_COLOR);

                if (main.gt.searchedNodes.contains(node)) {
                    g.setColor(Color.YELLOW);
                }
                else if(main.gt.origin != null && node.equals(main.gt.origin))
                    g.setColor(ORIGIN_NODE_COLOR);
            
                else if(main.gt.destination != null && node.equals(main.gt.destination)){
                    g.setColor(DESTINATION_NODE_COLOR);
                }
                g.fillRect(x, y, w, h);
            }
        }
        


        // Method to add a node to the list for drawing
        public void drawNodes(Graph graph) {
            nodesToDraw = graph.grid.keySet();
            // repaint();  // Trigger a repaint to show the newly added nodes
        }
    }
}