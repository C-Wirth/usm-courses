package flightpack;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 
 * Author: Colby Wirth 
 * Version: 5 October 2024 
 * Course: COS 285 
 * Class: QueueSimulator.java
 */
public class QueueSimulator {

    private final ArrayList<Flight> aList; // save input data -- sorted vy Flight
    private final MyQueue<Flight> queue; //used for similation
    private final int processTime; // total process time in each day based on counters

    /**
     * Constructor for a QueueSimulator object
     * @param aList the ArrayList<Flight> from a specified airport
     * @param numberCounter number of counters required
     */
    public QueueSimulator(ArrayList<Flight> aList, int numberCounter){
        
        this.aList = aList; 
        processTime = numberCounter*3600;
        queue = new MyQueue<>();
    }

    /**
     * This runs the main logic of the simulation - processes each day
     * @return true if the simulation is completed with the number of counters 
     * @return false if more counters are needed
     */
    public boolean simulation(){

        LocalDateTime ldtStart = aList.get(0).getFlightDate();
        ldtStart = ldtStart.minusHours(1);
        LocalDateTime ldtEnd = aList.get(aList.size()-1).getFlightDate();

        for (LocalDateTime ldt = ldtStart; ldt.isBefore(ldtEnd); ldt = ldt.plusHours(1)) {
           
            addPassengers(ldt);
            
            if (!processQueue()) {
                return false;
            }
        }
        return true;
    }
        

    /**
     * helper method for simulation processes the timing for a queue of passengers
     * 
     * @return true if all passengers are processed in the alotted time
     * @return false if not all passengers are processed in the time
     */
    private boolean processQueue(){

        int currentTime = processTime;

        while(!queue.isEmpty()&& currentTime >= 0){
            
            Flight temp = queue.poll();
            int passengersProcessTime = temp.getPassengers()*6;
            currentTime -= passengersProcessTime;
        }
        return queue.isEmpty();
    }

    /**
     * helper method for simulation adds values passengers to queue for processing
     * @param ldt the hour of processing
     */
    private void addPassengers(LocalDateTime ldt){

       while (aList.get(0).getFlightDate().equals(ldt.plusHours(1))){

            Flight temp= aList.get(0);
            queue.offer(temp);
            aList.remove(0);

            if(aList.isEmpty()){
                return;
            }
       }
    }
}
