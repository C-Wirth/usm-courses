package flightpack;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * Author: Colby Wirth 
 * Version: 20 November 2024 
 * Course: COS 285 
 * Class: program4.java
 */
public class program4 {

    private static final MyDataReader DATA_READER = new MyDataReader();

   /** 
    * Main method for program 4 calls simulations on specified airports
    */
    public static void main(String[] args) throws IOException{

            //Default values if non are passed into args - so the program will always compile
            String filePath = "src/flights.csv";
            String state = "ME";
            String airport = "PWM";

        if (args.length !=0){
            filePath = args[0];
            state = args[1];
            airport = args[2];
        }

        simRunner(airport, state, filePath);
    }

    /**
     * this method handles running similations to find how many counters are needed per airport
     * @param airport the airport name to run the simulation with
     * @throws IOException
     */
    public static void simRunner(String airport, String state, String filePath) throws IOException{


        long startTime = System.currentTimeMillis();
        ArrayList<Flight> flights = DATA_READER.FlightSorted(airport, filePath);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Seconds to read the flights from: " + state + " " +  airport + " " + elapsedTime);
       
        int i = 1;
        startTime = System.currentTimeMillis();

        while(! new QueueSimulator(flights, i).simulation()){
            i+=1;
        }

        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Seconds to find the minimum number of counters for: " + airport + " " + elapsedTime);
        System.out.println("Minimum number of counters for: " + airport + ": " + i + "\n");
    }
}
