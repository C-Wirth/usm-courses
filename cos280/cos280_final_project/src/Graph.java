import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class Graph {

    final int ORTHOGONAL_LOW_BOUND = 10;
    final int ORTHOGONAL_UPPER_BOUND = 20;

    final int DIAGONAL_LOWER_BOUND = 14;
    final int DIAGONAL_UPPER_BOUND = 24;

    int columns, rows;

    static int orthogonalHeuristic = main.orthogonalHeuristic;
    static int diagonalHeuristic = main.diagonalHeuristic;
    
    static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, //Horizontal and vertical neighbors
                                    {1, 1}, {1, -1}, {-1,  1}, {-1, -1}}; // Diagonal neighbors

    HashMap<Point2D, LinkedList<Edge<Point2D,Integer>>> grid;

    public Integer seed;  // used for generating pseudo-random lengths for edges - when the corect constructor is called
    public Random rand; //same as above

    /**
     * Defauly Constructor for a graph
     * Builds all node anc calls connectNodes to create edges
     * if seed != null, the edges will be creted with psuedo-randomness from seed
     * @param rows number of rows
     * @param columns number of columns
     */
    Graph(int columns, int rows, Integer seed){

        this.columns = columns;
        this.rows = rows;

        this.seed = seed; // Initialize the random generator with the seed if it not equal to null
        if(seed != null)
            this.rand = new Random(seed);  

        grid = new HashMap<>();
        createGrid();
  
        for (int c = 0; c < columns; c++){
            for (int r = 0; r < rows; r++) 
              connectNodes(new Point2D.Double(c, r));
        }
    }

    static class Edge<K, V> {
        public final K adjacentNode;
        public final V weight;

        public Edge(K adjacentNode, V weight) {
            this.adjacentNode = adjacentNode;
            this.weight = weight;
        }
    }

    /**
     * This method creates a grid by call from the constructor
     */
    private void createGrid() {
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                grid.put(new Point2D.Double(c,r), new LinkedList<>());
            }
        }
    }

    /**
     * this method connects two nodes in Graph, random or standard
     */
    private void connectNodes(Point2D current) {

        int col = (int) current.getX();
        int row = (int) current.getY();

        int orthogonalDistance = orthogonalHeuristic;
        int diagonalDistance = diagonalHeuristic;

        if (seed != null) {
            orthogonalDistance = rand.nextInt(ORTHOGONAL_LOW_BOUND,ORTHOGONAL_UPPER_BOUND);
            diagonalDistance = rand.nextInt(DIAGONAL_LOWER_BOUND,DIAGONAL_UPPER_BOUND);
        }

            for (int[] dir : DIRECTIONS) { //check all potential eight neighbors
                int newCol = col + dir[0];
                int newRow = row + dir[1];
                Point2D neighbor = new Point2D.Double(newCol, newRow);

                if (grid.containsKey(neighbor)) { //if the direction exists add the edge betwen two nodes
                    int distance = (Math.abs(dir[0]) + Math.abs(dir[1]) == 2) //determine if the edge is diagonal or orthogonal
                        ? diagonalDistance : orthogonalDistance;
                    addEdge(current, neighbor, distance);
            }
        }
    }

    private void addEdge(Point2D from, Point2D to, int distance){
        
        //the following two expressions were generated from chat GPT on 10-26-2024 to help handle the logic
        if (grid.get(from).stream().noneMatch(pair -> pair.adjacentNode.equals(to))) //does this edge exists from 'from' to 'to'
            grid.get(from).add(new Edge<>(to, distance));

        if (grid.get(to).stream().noneMatch(pair -> pair.adjacentNode.equals(from))) //does this edge already exist from 'to' to 'from'
            grid.get(to).add(new Edge<>(from, distance));

    }

    /**
     * Standard toString method for a Graph
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                
                Point2D current = new Point2D.Double(c,r);

                if(!grid.containsKey(current))
                    continue ;

                str.append("Node in column ").append(c).append(", row ").append(r).append("): ")
                  .append(grid.get(current).size()).append(" relations\n");
            }
        }
        
        return str.toString();
    }

    /**
     * Method to delete a Node
     * @param r,c the row and column of the Node to be deleted
     */
    public void deleteNode(Point2D node) {
        
        if(!grid.containsKey(node))
            throw new NullPointerException("Node is null at index [" + node.getX() + "][" + node.getY() + "]");

        for (int[] dir : DIRECTIONS) {
            int newCol = (int) (node.getX() + dir[0]);
            int newRow = (int) (node.getY() + dir[1]);

            Point2D pairedNode = new Point2D.Double(newCol,newRow);

            if (grid.containsKey(pairedNode))
                grid.get(pairedNode).removeIf(pair -> pair.adjacentNode.equals(node));  // Removes the edge to 'node'
        }

        if(node == main.gt.origin){ //means to handle GraphTraversal visualization properies not needede for Graph class Operations
            main.gt.origin = null;
        }
        else if(node== main.gt.destination){ //means to handle GraphTraversal visualization properies not needede for Graph class Operations
            main.gt.destination = null;
        }

        grid.remove(node);
    }

    public void addNode(Point2D node) throws NodeAlreadyExists {
    
        if(grid.containsKey(node))
                throw new NodeAlreadyExists("Node already exists at position (" + node.getX() + ", " + node.getY() + ")");
            

        grid.put(node, new LinkedList<>());
            connectNodes(node);
    }
    /**
     * This inner class handles the exception of if a Node is attempted to be created in a location where a Node already exists
     */
    public class NodeAlreadyExists extends Exception {
            public NodeAlreadyExists(String message) {
                super(message);
            }
    }

    /**
     * Dijkstra's algorithm
     * @param origin where the algorithm begins
     * @param destination goal end node
     * @return a LinkedList<Point2D> the shortest path to desetination from origin
     */


    public LinkedList<Point2D> dijkstra(Point2D origin, Point2D destination) {

        if (origin == null || destination == null) 
            return new LinkedList<>();
    
        Map<Point2D, Integer> edgeDistances = new HashMap<>();
        Map<Point2D, Point2D> previousPaths = new HashMap<>();
        Set<Point2D> visitedNodes = new HashSet<>();
        PriorityQueue<Edge<Point2D, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));
    
        edgeDistances.put(origin, 0);
        pq.add(new Edge<>(origin, 0));
    
        while (!pq.isEmpty()) {
            Point2D current = pq.poll().adjacentNode;
            if (!visitedNodes.add(current))
                continue; // Add and check if the node was already visited
    
            if (current.equals(destination))
                break;
    
            main.gt.searchedNodes.add(current);
    
            for (Edge<Point2D, Integer> neighbor : main.gt.graph.grid.getOrDefault(current, new LinkedList<>())) { //look for a short path from all of current's neighbors
                Point2D nextNeighbor = neighbor.adjacentNode;
                if (visitedNodes.contains(nextNeighbor)) //node has been visited, skip
                    continue;
    
                int newDist = edgeDistances.get(current) + neighbor.weight;
                if (newDist < edgeDistances.getOrDefault(nextNeighbor, Integer.MAX_VALUE)) { //we've found a new shortest path to a node, add it to the queue
                    edgeDistances.put(nextNeighbor, newDist);
                    previousPaths.put(nextNeighbor, current);
                    pq.add(new Edge<>(nextNeighbor, newDist));
                }
            }
        }

        LinkedList<Point2D> shortestPath = new LinkedList<>(); //build the shortest path

        System.out.println(visitedNodes.size()); // for anaylsis
    
        for (Point2D step = destination; step != null; step = previousPaths.get(step))
            shortestPath.addFirst(step);

        return previousPaths.containsKey(destination) || destination.equals(origin) ? shortestPath : new LinkedList<>(); //if a path hasnt been found return an empty LL
        }
    
    /**
     * aStar algorithm for graph traversal 
     * @param origin
     * @param destination
     * @return the shortest path, a LinkedList
     */
    public LinkedList<Point2D> aStar(Point2D origin, Point2D destination) {

        if(origin == null || destination == null)
            return new LinkedList<>();

        Map<Point2D, Double> gScores = new HashMap<>(); //the cost to reach a node from destination
        Map<Point2D, Double> fScores = new HashMap<>(); // f = g+h where h is the heuristic distance
        Map<Point2D, Point2D> previousPaths = new HashMap<>();
        Set<Point2D> visitedNodes = new HashSet<>();
        PriorityQueue<Edge<Point2D, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(p -> p.weight));
    

        gScores.put(origin, 0.0);

        if(main.heuristic.equals("euc"))
            fScores.put(origin, euclideanCalculator(origin, destination));

        else if(main.heuristic .equals("man"))
        fScores.put(origin, manhattanCalculator(origin, destination));
        
        else
            fScores.put(origin, octileCalculator(origin, destination));



        pq.add(new Edge<>(origin, fScores.get(origin)));  //equation is f= g + h, e = 0 here

        while(!pq.isEmpty()){
            
            Point2D current = pq.poll().adjacentNode;

            // System.out.println("Visiting: " + current + " with fScore: " + fScores.get(current));


            if(!visitedNodes.add(current))
                continue; //add and check if the node was already visited

            if(current.equals(destination))
                break;

            main.gt.searchedNodes.add(current);

            for (Edge<Point2D, Integer> neighbor : main.gt.graph.grid.getOrDefault(current, new LinkedList<>())) { //look for at all edges from all of current's neighbors

                Point2D nextNeighbor = neighbor.adjacentNode;

                double edgeCost = neighbor.weight;
                double tentativeGScore = gScores.get(current) + edgeCost;
    
                if (visitedNodes.contains(nextNeighbor))
                    continue; // Skip if already visited

                 // If this path to the neighbor is better, update paths and scores
                 if (tentativeGScore < gScores.getOrDefault(nextNeighbor, Double.MAX_VALUE)) {
                    previousPaths.put(nextNeighbor, current);
                    gScores.put(nextNeighbor, tentativeGScore);

                    double totalEstimate;

                    //determine which heurisitc was inputted
                    if(main.heuristic.equals("euc"))
                        totalEstimate = tentativeGScore + euclideanCalculator(nextNeighbor, destination);     
                    else if(main.heuristic .equals("man"))
                        totalEstimate = tentativeGScore + manhattanCalculator(nextNeighbor, destination);
                    else
                        totalEstimate = tentativeGScore + octileCalculator(nextNeighbor, destination);

                    fScores.put(nextNeighbor, totalEstimate);

                    // Add or re-add the neighbor to the PriorityQueue
                    pq.add(new Edge<>(nextNeighbor, totalEstimate));
            }
        }
    }
        System.out.println(visitedNodes.size()); // for anaylsis

        LinkedList<Point2D> shortestPath = new LinkedList<>();
        for (Point2D step = destination; step != null; step = previousPaths.get(step)) 
            shortestPath.addFirst(step);

        return previousPaths.containsKey(destination) || destination.equals(origin) ? shortestPath : new LinkedList<>();

    }

    /**
     * helper method for A* to calculate distances with Octile calculation
     * @param current the node being traversed
     * @param destination the goal node
     * @return the eudlidean distance bewteen two nodes as a dounle
     */
    public Double octileCalculator(Point2D current, Point2D destination){ 
        
        double dx = Math.abs(current.getX() - destination.getX());
        double dy = Math.abs(current.getY() - destination.getY());
        return orthogonalHeuristic * (dx + dy) + (diagonalHeuristic - orthogonalHeuristic) * Math.min(dx, dy);    
    }

     /**
     * helper method for A* to calculate distances with euclidean dist
     * @param current the node being traversed
     * @param destination the goal node
     * @return the eudlidean distance bewteen two nodes, a double
     */
    public Double euclideanCalculator(Point2D current, Point2D destination){ 
        
        double dx = current.getX() - destination.getX();
        double dy = current.getY() - destination.getY();
        return Math.sqrt(dx * dx + dy * dy);  
    }

    /**
     * helper method for A* to calculate distances with manhattan calculation
     * @param current the node being traversed
     * @param destination the goal node
     * @return the manhattan distance between two nodes, a double
     */
    public Double manhattanCalculator(Point2D current, Point2D destination) {
        double dx = Math.abs(current.getX() - destination.getX());
        double dy = Math.abs(current.getY() - destination.getY());
        return orthogonalHeuristic * (dx + dy);
    }
}
