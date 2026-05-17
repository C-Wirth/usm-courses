package flightpack;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Author: Colby Wirth
 * Version: 4 October 2024 
 * Course: COS 285 
 * Class : MyArrayList.java
 */public class MyArrayList<T extends Comparable<T>>{

    private T[] elements;

    private int size = 0;
    
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unused")
    private int capacity ;

    /**
     * Default constructor for MyArrayList object
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {

        elements = (T[]) new Comparable[DEFAULT_CAPACITY];

    }

     /**
      * Adds an generic element, 'T' to the ArrayList
      *
      * @param element, a generic type
      */
     public void add(T element){

        if(size == elements.length)
            ensureCapacity();
        
        elements[size] = element;

        size+=1;

     }

     /**
      * Helper method for add method
      * Doubles the size of an ArrayList.
      */
     private void ensureCapacity(){

       elements = Arrays.copyOf(elements, elements.length*2);

     }

     /**
      * This method returns an element at an index specified by parameter 'index'
      *
      * @param index, the place in the MyArrayList to find an element at 
      * @return the generic type at a given index
      * @throws IndexOutOfBoundsException
      *
      */
     public T get(int index){

        if(index >= this.size || index< 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        return elements[index];
     }

     /**
      * This method returns the size of a MyArrayList object 
      *
      * @return size, how many elements are in the MyArrayList
      */
     public int size(){
        return this.size;
     }

     /**
      * A sort method that implements insertion sort.  Organizes elements by natural ordering
      */
    public void sort(){

        for (int i = 1; i < this.size; i++){
            for (int j = i; j > 0 && (elements[j-1]).compareTo(elements[j]) > 0; j--){

                T temp = elements[j];
                elements[j] = elements[j-1];
                elements[j-1] = temp;

            }        
        }
    }

    /**
     * This is an insertion sort method by insertion sort with a Comparator object to specify how to sort
     * 
     * @param comparator the comparator object that implements the sorting algorithm
     */
    public void sort(Comparator<? super T> comparator){
        
        for (int i = 1; i < this.size; i++){
            for (int j = i; j > 0 && comparator.compare(elements[j-1], elements[j]) > 0; j--){

                T temp = elements[j];
                elements[j] = elements[j-1];
                elements[j-1] = temp;
            }        
        }
    }
}

