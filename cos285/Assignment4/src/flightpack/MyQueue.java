package flightpack;

import java.util.ArrayList;

/**
 * Author: Colby Wirth 
 * Version: 4 October 2024 
 * Course: COS 285 
 * Class: MyQueue.java 
 * Uses generic types - implemented with an ArrayList as an underlying data structure
 * @param <E>
 */
public class MyQueue<T>{

    private final ArrayList<T> queue;

    
    /**
     * Constructor for generic MyQueue object with an ArrayList<T>
     */
    public MyQueue(){

        queue = new ArrayList<>();

    }

    /**
     * offer method by for a MyQueue
     * @param input a generic Type T
     * @return a boolean True if input as added, False if input was not added
     */
    public boolean offer(T input){
        return queue.add(input);
    }

    /**
     * poll method for a MyQueue object
     * @return the object at the beginning of the Queue, type T
     */
    public T poll(){
        return queue.removeFirst();
    }

    /**
     * isEmpty method for a MyQueue object
     * @return True if the MyQueue is empty, False if MyQueue is not empty
     */
    public boolean isEmpty(){
        return queue.isEmpty();

    }

    /**
     * size method for a MyQueue object
     * @return the size of the Queue, an int
     */
    public int size(){
        return queue.size();

    }

    /**
     * peek method for a MyQueue object
     * @return the object at the front of the queue without removing it from the MyQueue object
     */
    public T peek(){
        return queue.getFirst();
    }

}
