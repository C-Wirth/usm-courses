Author: Colby Wirth

Assignment: Assignment 2

Course: COS 285 Data Structures

Instructor: Dr. Behrooz Mansouri

Last Update: 21 September 2024

In this project you will find: 
    
    -README.md
     flightpack package:
          -Airport.java
          -Flight.java
          -FlightComparator.java
          -MyArrayList.java
          -MyDataReader.java
          -program2.java **MAIN CLASS**

How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac flightpack.program2. 

     After compiling ALL classes, run main class with the command: 
          java </path/to/flightpack/program2> <path/to/flights.csv> <two letter state code> 
          ie: java flightpack/program2 flights.csv ME

About: 
     This program runs the main class called program2 and produces the following information from flights.csv with a given state parameter:

     i.   Time taken to construct an ArrayList<Flight> from 3 million CSV entries (I made two different ArrayLists, one for ex 6 and one for ex7, only one was timed)

     ii.  Time taken to perform MyArrayList's parameterless sorting algorithm on 3 million UNORDERED entries.

     iii. Time taken to perform MyArrayList's parameterless sorting algorithm on 3 million NATURALLY ORDERED entries.

     iv.  Time taken to perform MyArrayList's sorting algorithm with a FlightComparator parameter on 3 million UNORDERED entries.

     v.  Time taken to perform MyArrayList's sorting algorithm with a FlightComparator parameter on 3 million ORDERED entries.