package flightpack;

import java.util.Comparator;

/**
 * Author: Colby Wirth 
 * Version: 4 October 2024 
 * Course: COS 285 
 * Class: FlightDateComparator.java
 */
public class FlightDateComparator implements Comparator<Flight>{

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
