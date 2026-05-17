package flightpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Author: Colby Wirth 
 * Version: 4 October 2024 
 * Course: COS 285 
 * Class: MyDataReader.java
 */
public class MyDataReader {

    /**
     * 
     * FlightSorted returns a sorted ArrayList of Flights
     * 
     * @param originAirportName the Airport Origin to build the ArrayList<Flight> upon
     * @return flightsWithSameOrigin a sorted ArrayList of flights matching airport name 'Origin' 
     */
    public ArrayList<Flight> FlightSorted(String originAirportName, String filePath) throws IOException{

        ArrayList<Flight> flightsWithSameOrigin = new ArrayList<>();

        try (BufferedReader flightDataReader = new BufferedReader(new FileReader(filePath))) {

            flightDataReader.readLine();
            String curFlightInfo;
            Flight newFlight;

            while ((curFlightInfo = flightDataReader.readLine()) != null) {

                if ((newFlight = OriginChecker(curFlightInfo, originAirportName)) == null) {
                continue;
                }
                flightsWithSameOrigin.add(newFlight);
            }
        }

        Collections.sort(flightsWithSameOrigin, new FlightDateComparator());
        return flightsWithSameOrigin;
    }

    /**
     * This is a helper method for FlightSorted - 
     * checks if the current flight being parsed has the same Origin airport as the input
     * 
     * @param CurFlightInfo the String being parsed to creat a Flight object with
     * @param originAirportName the String of the Origin Airport to be matched
     * @return null if not a match | flightParser(flightElements) - this returns a Flight object with matching Origin Airport
     */
    private Flight OriginChecker(String curFlightInfo, String originAirportName){

        String[] flightElements = curFlightInfo.split(",");

             if (!flightElements[0].equals(originAirportName)){
                return null;
            }
            else{ 
                return flightParser(flightElements);
            }
    }

    /**
     * This method returns a MyArrayList<Flight> object with all Flight's with an originState attribute
     *       that matches the originState input parameter.
     * 
     * @param flightListPath the String to the csv file to read
     * @param originState the origin state to find
     * @return flightsList the MyArrayList<Flight> with an origin that matches originState
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static MyArrayList<Flight> OriginStateFinder(String flightListPath, String originState) throws FileNotFoundException, IOException {

        MyArrayList<Flight> flightList = new MyArrayList<>();

        try (BufferedReader flightDataReader = new BufferedReader(new FileReader(flightListPath))) {

            flightDataReader.readLine();

            String curFlightInfo;

            while ((curFlightInfo = flightDataReader.readLine()) != null) {

                String[] flightElements = curFlightInfo.split(",");

                if(flightElements[2].equals(originState))
                    flightList.add(flightParser(flightElements));
            }
        }
        return flightList;
    }


    /**
     *flightReader reads a file and creates a Flight[] with all elements
     * 
     * @param flightListPath String the path to the file log
     * @param numFlights the number to tnitiate the Array to
     * @return Flight[] an Array of Flight objects
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Flight[] flightReader(String flightListPath, int numFlights) throws FileNotFoundException, IOException {

        Flight[] flightList = new Flight[numFlights];

        try (BufferedReader flightDataReader = new BufferedReader(new FileReader(flightListPath))) {

            flightDataReader.readLine();

            String curFlightInfo;

            int i = 0;

            while ((curFlightInfo = flightDataReader.readLine()) != null) {

                String[] flightElements = curFlightInfo.split(",");

                flightList[i] = flightParser(flightElements);

                i++;
            }
        }
        return flightList;
    }

    /**
     * DateTimeFormatter resource retrieved from
     * https://docs.oracle.com/en%2Fjava%2Fjavase%2F22%2Fdocs%2Fapi%2F%2F/java.base/java/time/format/DateTimeFormatter.html
     *
     * This is a helper method to flightReader, parses each line of inputted
     * flightlog csv file
     *
     * @param flightElements Sting[] of flight information for creating Flight
     * object
     * @return flight newly created Flight object from parsed String[]
     * flightElements
     */
    private static Flight flightParser(String[] flightElements) {

        Airport departure = new Airport(flightElements[0], flightElements[1], flightElements[2]);

        Airport destination = new Airport(flightElements[3], flightElements[4], flightElements[5]);
        String dateTimeString = flightElements[9];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH");
        LocalDateTime dateAndTime = LocalDateTime.parse(dateTimeString, formatter);

        Flight flight = new Flight(
                departure,
                destination,
                dateAndTime,
                Integer.parseInt(flightElements[6]),
                Integer.parseInt(flightElements[7]),
                Integer.parseInt(flightElements[8])
        );
        return flight;
    }
}
