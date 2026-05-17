package flightpack;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Colby Wirth Version: 12 September 2024 Course: COS 285 Class:
 * MyAnalyzer.java
 */
public class MyAnalyzer {

    /**
     * This method returns a set of Strings of airport names dictated by state
     * from a given input of array of Flight objects
     *
     * Online Resourced Used when writing this method:
     * https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
     *
     * @param state String for State name - uses two letter state codes
     * @param flightArray Array of Flight Objects
     * @return airportsInState Set of Strings of Airport names
     */
    public static Set<String> airportFinderByState(String state, Flight[] flightArray) {
        Set<String> airportsInState = new HashSet<>();

        for (Flight flight : flightArray) {
            if (flight.getOrigin().getState().equals(state)) {
                airportsInState.add(flight.getOrigin().getAirportName());
            }
            if (flight.getDestination().getState().equals(state)) {
                airportsInState.add(flight.getDestination().getAirportName());
            }
        }
        return airportsInState;
    }

    /**
     * This method returns the max number of passengers that have been on a
     * flight to a specified airport, given an array of Flight objects
     *
     * @param destination Three letter code for an airport
     * @param flightArray Array of Flight Objects
     * @return max An int representing the largest amount of people of a given
     * flight to an airport
     */
    public static int maxPassengersToAirport(String destination, Flight[] flightArray) {
        int max = 0;

        for (Flight flight : flightArray) {

            if (flight.getDestination().getAirportName().equals(destination) && flight.getPassengers() > max) {
                max = flight.getPassengers();
            }
        }
        return max;
    }

    /**
     * String.format source retrived from
     * https://www.geeksforgeeks.org/java-string-format-method-with-examples/
     *
     * This method returns a percentage represented as a String with the number
     * of full flights given an Array of Flight objects
     *
     * @param flightArray Array of Flight Objects
     * @return String Percentage of full flights from a given data set
     */
    public static String fullCapacityRate(Flight[] flightArray) {
        int atMaxCap = 0;

        for (Flight flight : flightArray) {
            if (flight.getPassengers() == flight.getSeats()) {
                atMaxCap += 1;
            }
        }
        return String.format("%.2f", 100 * ((double) atMaxCap / flightArray.length)) + "%";
    }

    /**
     * This method calculates by int divisionn the average number of people on a
     * flight going to specifed state from a specifed airport in a specified
     * year
     *
     * @param flightArray Array of Flight Objects
     * @param airport String of three letter airport name code
     * @param state String of two letter state name
     * @param year int of year of flight records
     * @return
     */
    public static int averagePassengerFromAirportToStateInYear(Flight[] flightArray, String airport, String state, int year) {

        int flightCount = 0;
        int totalPassengerCount = 0;

        for (Flight flight : flightArray) {

            if (flight.getOrigin().getAirportName().equals(airport)
                    && flight.getDestination().getState().equals(state)
                    && flight.getFlightDate().getYear() == year) {
                flightCount++;
                totalPassengerCount += flight.getPassengers();
            }
        }
        return totalPassengerCount / flightCount;
    }
}
