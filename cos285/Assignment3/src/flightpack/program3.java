package flightpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Colby Wirth 
 * Version: 1 October 2024 
 * Course: COS 285 
 * Class: program3.java
 */
 public class program3 {
    
    /**
     * main method runs Assignment 3
     * @param args args[0] path/to/flights.csv, args[1] state, args[2] airport1, args[3] airport2, args[4] airport3
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        // String path = args[0];
        // String state = args[1];
        // String airport1 = args[2];
        // String airport2 = args[3];
        // String airport3 = args[4];

        String path = "path/to/flights.csv";
        String state = "ME";
        String airport1 = "PWM";
        String airport2 = "AUG";
        String airport3 = "BGR";

        MyLinkedList flights = flightReaderByState(path, state);

        System.out.println("Printing the number of passengers from [1994, 2009] from PWM:");
            mainIteration(flights, airport1);
        
        System.out.println("Printing the number of passengers from [1994, 2009] from BGR:");
            mainIteration(flights, airport2);
        
        System.out.println("Printing the number of passengers from [1994, 2009] from AUG:");
            mainIteration(flights, airport3);

    }

    /**
     * helper method for main method - executes all iterations and printing of counts per year
     * @param flights the MyLinkedlList
     * @param airport the Aiport chain to iterate through
     */
    private static void mainIteration(MyLinkedList flights, String airport){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH");
        LocalDateTime min = LocalDateTime.parse("1994-01-01:01", formatter);
        LocalDateTime max = LocalDateTime.parse("2009-12-31:23", formatter);

        LocalDateTime curMax = min.plusYears(1);


        while(curMax.getYear() <= max.getYear()){

            MyLinkedList.MyItr iterator = flights.iterator(airport, min, curMax);

            int count = 0;

            while(iterator.hasNext()){//issue here - FIX ME
                count+= iterator.current.data.getPassengers();
                iterator.next();
            }
            System.out.println("" + curMax.getYear() + ", " + count);
            curMax = curMax.plusYears(1);
        }
    }


    /**
     * this method reads the CSV file and Nodes with Flight objects.
     * 
     * @param path the path to files path
     * @param state the origin State to limit the myLL to
     * @return myLL a LinkedList with all data read from 'path'
     */
    public static MyLinkedList flightReaderByState(String path, String state) throws FileNotFoundException, IOException{

        MyLinkedList myLL = new MyLinkedList();

        try (BufferedReader flightDataReader = new BufferedReader(new FileReader(path))) {

            flightDataReader.readLine();

            String curFlightInfo;

            while ((curFlightInfo = flightDataReader.readLine()) != null) {

                String[] flightElements = curFlightInfo.split(",");

                        if (!flightElements[2].equals(state)){
                            continue;
                        }
                 MyLinkedList.Node newNode = new MyLinkedList.Node(flightParser(flightElements));

                myLL.add(newNode);
            }
        }
        return myLL;
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