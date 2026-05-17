package flightpack;

// import flightpack.Flight;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Colby Wirth 
 * Version: 1 October 2024 
 * Course: COS 285 
 * Class: MyLinkedList.java
 */
public class MyLinkedList{
    int size ;
    Node head;

    /**
     * Default contructor
     */
    public MyLinkedList(){
        size = 0;
        head = new Node(null);
    }

    /**
     * Node Class - a static class within MyLinkedList that encapsulates each Node from a MyLinkedList object 
     */
    public static class Node{
        Node next;
        Node nextAirport;
        Flight data;

        /**
         * Constructor for a Node
         * @param next the next element in the MyLinkedList
         * @param nextFlight the next Node with the same Origin-Flight attribue
         */
        public Node(Flight data){
            this.next = null;
            this.nextAirport = null;
            this.data = data;
        }
    }

    /**
     * Returns the size of a MyLinkedList object
     * @return size, how many elements in the MyLinkedList, an int
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Add method used to appends a node to an ordered MyLinkedList object -maintains order
     * Nodes are ordered by LocalDateTime-Flight attribute
     * @param newNode a Node with Flight datatype
     */
    public void add(Node newNode){

        size++;

        FlightCompareByDate c = new FlightCompareByDate();
        Node current = head;

        while (current.next != null && c.compare(newNode.data, current.next.data) > 0) { //return -1 if newNode is before current.next
            current = current.next;
        }
    
        newNode.next = current.next;
        current.next = newNode;
        
        if (size > 1) 
            flightOriginFixer(newNode);
    }

        /**
         * This method inserts the newNode into its respective getAirportName chain
         * @param newNode the new Node to be appeneded
         */
        private void flightOriginFixer(Node newNode){

            Node current = head.next;
            Node lastOrigin = null;

            while(current != null && current != newNode){
                if(current.data.getOrigin().getAirportName().equals(newNode.data.getOrigin().getAirportName())){
                    lastOrigin = current;
                }
                current = current.next;
            }
            if(lastOrigin != null){
                newNode.nextAirport = lastOrigin.nextAirport;
                lastOrigin.nextAirport = newNode;
                return;
            }

            while(current != null && !(current.data.getOrigin().getAirportName().equals(newNode.data.getOrigin().getAirportName()))){
                current = current.next;
            }
            if(current != null && current != newNode){
                newNode.nextAirport = current;
            }                
        }
        
        /**
         * Compares two Node's FlightDate attributes (LocalDateTime object)
         * @implements Comparator - Compares Flight objects
         */
    public class FlightCompareByDate implements Comparator<Flight>{
     
        /**
        * compare method compares two Flight object's DateTime attributes
        * 
        * @Override Comparator's compare method
        * @return utilize java's String's compareTo method to compare a Flight's origin AirportName field
        */
        @Override
        public int compare(Flight f1, Flight f2) {
            return f1.getFlightDate().compareTo(f2.getFlightDate()); //return -1 if f1 is before f2
        }
    }

    /**
     * Builds a new MyItr objected
     * @param airport the airports to iterate through
     * @param start the beginning LocalDateTime begin iteration
     * @param end the last LocalDateTime to finish iterating at
     * @return a MyItr iterator ready to iterate
     */
   public MyItr iterator(String airport, LocalDateTime start, LocalDateTime end){
        return new MyItr(airport, start, end);
   }

   /**
    * A class that returns an Iterator for a MyLinkedList object
    @implements Iterator iterates through Flight objects
    */
   class MyItr implements Iterator<Flight>{        
        
        LocalDateTime end;
        Node current = null;

        /**
         * 
         * Constructor for a MyItr
         * @param start the beginning LocalDateTime begin iteration
         * @param airport the airports to iterate through
        * @param end the last LocalDateTime to finish iterating at
        * @return a MyItr iterator ready to iterate
        */
        public MyItr(String airport, LocalDateTime start, LocalDateTime end) {
            
            this.end = end;

            Node tempNode = head.next;

            while (tempNode != null) {

                if (tempNode.data.getOrigin().getAirportName().equals(airport) 
                    && tempNode.data.getFlightDate().compareTo(start) >= 0) {
                    current = tempNode;
                    break;
                }
                tempNode = tempNode.next;
            }
        }

        /**
         * Checks if current Node is not null and below upper bound 'end'
         * @return False if current is null or out of bounds
         * @return True if current is non-null and inbounds
         */
        @Override
        public boolean hasNext() {

            return current != null && current.data.getFlightDate().compareTo(end) < 1;
        }

        // Code for NoSuchElementException recevied from:
        //https://stackoverflow.com/questions/7630014/difficulty-throwing-nosuchelementexception-in-java
        @Override
        /**
         * Move the iterator to the element in the MyLinkedList
         */
        public Flight next() {

            if (!hasNext()){
                throw new NoSuchElementException();
            }
     
            Flight retVal = current.data;
            current = current.next;
                return retVal;
        }
    }
    
}