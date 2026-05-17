package flightpack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TEST CLASS FOR MyLinkedList
 * 
 * Author: Colby Wirth 
 * Version: 1 October 2024 
 * Course: COS 285 
 * Class: test.java
 */
public class test {


    public static void main(String[] args){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH");

    Airport PWM = new Airport("PWM", "Portland", "ME");
    Airport BGR = new Airport("BGR", "Bangor", "ME");

    Airport SEA = new Airport("SEA", "Seattle", "WA");

    LocalDateTime d1 = LocalDateTime.parse("2000-10-16:11", formatter);
    Flight f1 = new Flight(SEA, PWM, d1, 0, 0, 0);

    LocalDateTime d2 = LocalDateTime.parse("2001-10-16:11", formatter);
    Flight f2 = new Flight(BGR, SEA, d2, 0, 0, 0);

    LocalDateTime d3 = LocalDateTime.parse("2005-10-16:11", formatter);
    Flight f3 = new Flight(SEA, PWM, d3, 0, 0, 0);

    LocalDateTime d0 = LocalDateTime.parse("1999-10-16:11", formatter);
    Flight f0 = new Flight(SEA, PWM, d0, 0, 0, 0);


    MyLinkedList  myList = new MyLinkedList();

    MyLinkedList.Node node1 = new MyLinkedList.Node(f1);
    myList.add(node1);

    MyLinkedList.Node node2 = new MyLinkedList.Node(f2);
    myList.add(node2);

    MyLinkedList.Node node3 = new MyLinkedList.Node(f3);
    myList.add(node3);

    MyLinkedList.Node node0 = new MyLinkedList.Node(f0);
    myList.add(node0);
    }
}
