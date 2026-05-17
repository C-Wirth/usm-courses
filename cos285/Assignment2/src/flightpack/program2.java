package flightpack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

/**
 * Author: Colby Wirth 
 * Version: 21 September 2024 
 * Course: COS 285 
 * Class: program2.java
 */
public class program2 {

    /**
     * main method runs flightpack program
     * @param args should be <flights.csv> <ME>
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        String flightLogPath = args[0];
        String state = args[1];

        long startTime = System.currentTimeMillis();
        MyArrayList<Flight> flightsForExercise6 = MyDataReader.OriginStateFinder(flightLogPath,state);
        long elapsedTime = System.currentTimeMillis() - startTime;

        MyArrayList<Flight> flightsForExercise7 = MyDataReader.OriginStateFinder(flightLogPath,state);
        System.out.println(elapsedTime + " milliseconds to read the file.\n");


        mainHelper(flightsForExercise6, "6.a");
        mainHelper(flightsForExercise6, "6.b");

        Comparator<Flight> c = new FlightComparator();
        mainHelper(flightsForExercise7, (FlightComparator) c, "7.a");
        mainHelper(flightsForExercise7, (FlightComparator) c, "7.b");
    }

    /**
     * mainHelper executes sorting and system timing for exercies 6.a or 6.b.  
     * More generally it takes a MyArrayList<Flight> object, an exercise name, and performs the insertion sort method 
     * @param listName the name of a MyArrayList<FLight> object
     * @param exerciseName what exercise is being performed
     */
    public static void mainHelper(MyArrayList<Flight>  listName, String exerciseName){
        long startTime = System.currentTimeMillis();
        listName.sort();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(elapsedTime + "  milliseconds to sort based on DateTime for exercise (" + exerciseName + ")\n");
    }
    /**
     * This method Overloads mainHelper and executes sorting and system timing for exercies 7.a or 7.b.  
     * More generally it takes a MyArrayList<Flight> object, a FlightComparator object, an exercise name, and performs the insertion sort method 
     * @param listName the name of a MyArrayList<FLight> object
     * @param comparator the Comparator object used for comparison between Flight objects
     * @param exerciseName what exercise is being performed
     */
    public static void mainHelper(MyArrayList<Flight>  listName, FlightComparator comparator, String exerciseName){
        long startTime = System.currentTimeMillis();
        listName.sort(comparator);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(elapsedTime + "  milliseconds to sort based on origin for exercise (" + exerciseName + ")\n");
    }
}