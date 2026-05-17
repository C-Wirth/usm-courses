package flightpack;

/**
 * Author: Colby Wirth 
 * Version: 21 September 2024 
 * Course: COS 285 
 * Class: Airport.java
 */
public class Airport {

    private String airportName;

    private String city;

    private String state;

    /**
     * A constructor for an airport object
     *
     * @param airportName the name of an aiport, a String
     * @param city the nanme of the airport's city, a String
     * @param state the two letter code of the airport's state, a String
     */
    public Airport(String airportName, String city, String state) {
        this.airportName = airportName;
        this.city = city;
        this.state = state;
    }

    /**
     * getter method for airporName field
     *
     * @return String airportName, three letter code
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * setter for airport name
     *
     * @param airportName String the airport's three letter code
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * getter method for city field
     *
     * @return String city
     */
    public String getCity() {
        return city;
    }

    /**
     * setter for city name
     *
     * @param city String the airport's city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * getter method for State value
     *
     * @return state String two letter state ID
     */
    public String getState() {
        return state;
    }

    /**
     * setter method for airport's state field
     *
     * @param state two letter state ID
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * toString used for debugging purposes
     * @Override Object class's toString method
     * @return this.airportName the three letter String for an airport
     */
    @Override
    public String toString() {
        return getAirportName();
    }

    
}
