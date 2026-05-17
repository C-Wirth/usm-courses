package flightpack;

import java.io.IOException;
import java.util.Set;

/**
 * Author: Colby Wirth Version: 12 September 2024 Course: COS 285 Class:
 * program1.java
 */
public class program1 {

    /**
     * Main method runs prescribed operations for flightpack,
     *
     * @param args Command Line Prompts - input a flighs.csv file
     * @throws IOException Thrown if I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        //info on how Timer works retrieved from https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java

        String flightLogPath = args[0];

        final int NUM_FLIGHTS = (int) 3e6;

        long startTime = System.currentTimeMillis();
        Flight[] flights = MyDataReader.flightReader(flightLogPath, NUM_FLIGHTS);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(elapsedTime + " milliseconds to read the file.\n");

        startTime = System.currentTimeMillis();
        Set<String> maineAirports = MyAnalyzer.airportFinderByState("ME", flights); // ex4.a method call
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Name of all the airports in the state of Maine:" + maineAirports.toString() + ".\n" + elapsedTime + " milliseconds for task 4.a.\n");

        startTime = System.currentTimeMillis();
        int maxPassengers = MyAnalyzer.maxPassengersToAirport("PWM", flights); // ex4.b method call
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("The maximum number of passengers coming to PWM with one flight: " + maxPassengers + ".\n" + elapsedTime + " milliseconds for task 4.b.\n");

        startTime = System.currentTimeMillis();
        String percentMaxCap = MyAnalyzer.fullCapacityRate(flights); // ex4.c method call
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("The percentage of the flights with no empty seats: " + percentMaxCap + ".\n" + elapsedTime + " milliseconds for task 4.c.\n");

        startTime = System.currentTimeMillis();
        int averagePassengers = MyAnalyzer.averagePassengerFromAirportToStateInYear(flights, "PWM", "FL", 2009); // ex4.d method call
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("The average number of passengers on flights from PWM to FL in 2009: " + averagePassengers + ".\n" + elapsedTime + " milliseconds for task 4.d.");

    }
}
