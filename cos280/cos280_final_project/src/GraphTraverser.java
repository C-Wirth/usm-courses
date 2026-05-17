
import java.awt.geom.Point2D;
import java.util.HashSet;

public class GraphTraverser {

Graph graph;
VisualFrame vf;
Point2D origin;
Point2D destination;
HashSet<Point2D> searchedNodes;

    /**
     * Generic constructor
     * @param graph encapsulated Graph
     * @param vf encapsulated VisualFrame
     */
    public GraphTraverser(Graph graph, VisualFrame vf){

        this.graph=graph;
        this.vf=vf;

        vf.panel.drawNodes(graph);
        
        searchedNodes = new HashSet<>();

    }

    /**
     * 
     * @param r the row
     * @param c the column
     * @throws Graph.NodeAlreadyExists
     */
    public void flipSquare(Point2D node) throws Graph.NodeAlreadyExists{

        if(node.equals(origin) || node.equals(destination))
            return;

        if(!graph.grid.containsKey(node))
           graph.addNode(node);
        else
            graph.deleteNode(node);
        
        vf.panel.drawNodes(graph);
    }

    /**
     * Hand;es the designation for origin and destination properties
     * @param r the row
     * @param c the column
     */
    public void flipDesignation(Point2D node){ //implement flipping for origin and destination

        if(!graph.grid.containsKey(node))
            return;

       if(origin == null && !node.equals(destination))
            origin = node;

        else if(node.equals(origin))
            origin = null;

        else if(destination == null)
            destination = node;
                
        else if(node.equals(destination) && !node.equals(origin))
             destination = null;

        //if a non-origin and non-destination square is right-clicked then do nothing.  There can only be one of each
        
        vf.panel.drawNodes(graph);
        
        } 
    }

