public class main {


    static GraphTraverser gt;

    //placeholder values with no input via args
    static String algorithm = "dijkstra"; 
    static String heuristic = "euc";
    static int numNodes = 100;
    static int orthogonalHeuristic = 14;
    static int diagonalHeuristic = 10;
    static boolean random = true;

    /**
     * Main class runs the Graph Visualization program
     * @param args
     */
    public static void main(String[] args) {
        
        if(args.length != 0){
            algorithm = args[0]; //aStar ot dijkstra
            heuristic = args[1]; //euc, man, or octile
            numNodes = Integer.parseInt(args[2]); // creates an nxn grid
            orthogonalHeuristic = Integer.parseInt(args[3]); // establish uniform orthogonal distance
            diagonalHeuristic = Integer.parseInt(args[4]); // establish uniform diagonal distance
            random = Boolean.parseBoolean(args[5]); // set randomly
        }

        Integer s1 = null;
        if (random == true)
            s1 = 100000000;

        int columns;
        int rows;
        int height;
        int width;
        int density;

        switch (numNodes) {
            case 100 -> {
                density = 50; //25 px squared per node
                columns=10;
                rows=10;
            }
            case 10000 -> {
                density = 8; //25 px squared per node
                columns=100;
                rows=100;
            }
            default -> {
                //numNodes == 1000000)
                
                density = 1; //25 px squared per node
                columns=1000;
                rows=1000;
            }
        }
        width = columns*density;
        height = rows*density;

        gt = new GraphTraverser(new Graph(columns, rows, s1), new VisualFrame(width,height,columns,rows)); 
    }

}
