package flightpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Colby Wirth Version: 12 September 2024 Course: COS 285 Class:
 * MyDataReader.java
 */
public class MyDataReader {

    /**
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
