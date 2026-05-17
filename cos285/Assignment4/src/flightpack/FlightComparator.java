package flightpack;

import java.util.Comparator;

/**
 * Author: Colby Wirth 
 * Version: 21 September 2024 
 * Course: COS 285 
 * Class: FlightComparator.java
 */
public class FlightComparator implements Comparator<Flight>{

    /**
     * compare method compares two Flight object's Origin AirportName Strings
     * 
     * @Override Comparator's compare method
     * @return utilize java's String's compareTo method to compare a Flight's origin AirportName field
     */
    @Override
    public int compare(Flight f1, Flight f2) {
        return f1.getOrigin().getAirportName().compareTo(f2.getOrigin().getAirportName());
    }

}
