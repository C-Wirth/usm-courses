package flightpack;

import java.time.LocalDateTime;

/**
 * Author: Colby Wirth Version: 12 September 2024 Course: COS 285 Class:
 * Flight.java
 */
public class Flight {

    private Airport origin;

    private Airport destination;

    private int passengers;

    private LocalDateTime flightDate;

    private int seats;

    private int distance;

    /**
     *
     * @param origin The Airport object which the flight departed
     * @param destination The Airport Object which the flight lands at
     * @param passengers Number of passengers on a flight, int
     * @param flightDate Date and time of a flight (yyyy-mm-dd:hh),
     * LocalDateTime object
     * @param seats Number of seats on a flight, int
     * @param distance Distance of the flight, int
     */
    public Flight(Airport origin, Airport destination, LocalDateTime flightDate, int passengers, int seats, int distance) {
        this.origin = origin;
        this.destination = destination;
        this.passengers = passengers;
        this.flightDate = flightDate;
        this.seats = seats;
        this.distance = distance;
    }

    /**
     * getter method for origin field
     *
     * @return origin the Airport object where the flight came from
     */
    public Airport getOrigin() {
        return origin;
    }

    /**
     * setter method for origin field
     *
     * @param origin the Airport object where the flight came from
     */
    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    /**
     * getter method for destination field
     *
     * @return destination the destination Airport object where the flight went
     * to
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * setter method for the destination field
     *
     * @param destination the destination Airport object where the flight went
     * to
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    /**
     * getter method for passenger field
     *
     * @return passengers the number of passengers, an int
     */
    public int getPassengers() {
        return passengers;
    }

    /**
     * setter method for passenger field
     *
     * @param passengers the number of passengers an int
     */
    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    /**
     * getter method for flightDate field
     *
     * @return flightDate the date and time of flight, a LocalDateTime object
     */
    public LocalDateTime getFlightDate() {
        return flightDate;
    }

    /**
     * setter method for flightDate field
     *
     * @param flightDate the date and time of a flight, a LocalDateTime object
     */
    public void setFlightDate(LocalDateTime flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * getter method for seats field
     *
     * @return sets the number of seats on a flight, int type
     */
    public int getSeats() {
        return seats;
    }

    /**
     * setter method for the seats field
     *
     * @param seats the number of seats on a flight, int type
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * getter method for the distance field
     *
     * @return distance the distance of the flight, an int
     */
    public int getDistance() {
        return distance;
    }

    /**
     * setter method, for the distance field
     *
     * @paramd distance the distance of a flight, an int
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }
}
