package twitterpack;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * Colby Wirth
 * COS 285
 * Version: 10 December 2024
 * This class implements Radix Sort and Quick Sort, and Merge Sort
 * 
 * Radix Sort: Only Implemented for custom Tweet objects - sorts by ID only - ID is an Integer
 * Quick Sort: Implemented for generic types, T.  - sorts by a Comparator
 * Mege Sort: Implemented for generic type, T. - uses intrensic .compareTo() to make all comparison
 * 
 * Comparators:
 * CompareByID - compares two custom Tweet objects by ID - an Integer
 * ComparatorByTime - compares two custom Tweet objects by it's LocalDateTime object field --- native to Java
 * 
 */
public class MySorts {

    /**
     * radix sort method - implemented with digit counts and running counts -instead of queues
     * @param array the input array
     * @param longestInt the size of the longest integer
     */
    public static void radixSort(Tweet[] array, int longestInt){

        Tweet[] outputArray = new Tweet[array.length];

        for(int i=1, modFactor=10 ; i<= longestInt ;i++, modFactor*=10){ //
            int[] digitCounts = new int[10];
            int[] runningCounts = new int[10];

            for(Tweet element : array){ //populate digitCounts
                int digit = (element.getId() % modFactor)/(modFactor/10);  // Extract the current digit
                digitCounts[digit]++; //always update digit count
            }

            runningCounts[0] = 0;
            for(int j = 1; j < 10 ; j++){ //update running counts
                runningCounts[j] = runningCounts[j-1]+digitCounts[j-1];
            }

            for (Tweet tweet : array) {
                int curDigit = (tweet.getId() % modFactor) / (modFactor/10);
                int curIndex = runningCounts[curDigit];
                outputArray[curIndex] = tweet; // fill the new updated array
                runningCounts[curDigit]++;
            }

            System.arraycopy(outputArray, 0, array, 0, array.length); // keep the outputArray stored in the original array
        }
    }

    /**
     * quicksort method driver
     * @param <T> generic type
     * @param array input array
     */
    public static <T extends Comparable<T>> void quickSort(T[] array, Comparator<T> comparator) {
        quickSort(array, 0, array.length-1, comparator);
    }

    /**
     * quicksort main rescursive logic 
     * @param <T> generic type
     * @param array the input array
     * @param lo low point for recusion
     * @param hi high point for recusion
     */
    private static <T> void quickSort(T[] array, int lo, int hi, Comparator<T> comparator) {

        if (lo >= hi) //recursive base case
            return;

        int pivotIndex = medianOfThree(array,lo,hi, comparator);

        pivotIndex = partition(array,pivotIndex,lo,hi, comparator);

        quickSort(array, lo, pivotIndex - 1, comparator);
        quickSort(array, pivotIndex + 1, hi ,comparator); //right subarray
    }

    /**
     * this method gets the pivot point with the median of thee apprach
     * @param array - the array to be sorted. lo - the low INDEX, hi, the high INDEX
     * @return the index of the median pivot point
     */
    private static <T> int medianOfThree(T[] array, int lo, int hi, Comparator<T> comparator){

        if (hi - lo < 3) //skip median of three approach if there are less than three elements --- the method will never handle 1 or less elements
            return comparator.compare(array[lo], array[hi]) > 0 ? hi : lo;


        int midPoint = lo + (hi - lo) / 2;

        if (comparator.compare(array[lo], array[midPoint]) > 0 ){ // array[lo] < array[hiPoint]
            swapElement(array, lo, midPoint);
        }
        if (comparator.compare(array[midPoint], array[hi])> 0 ){
            swapElement(array, midPoint, hi);
        }
        if (comparator.compare(array[lo], array[midPoint]) > 0 ){
            swapElement(array, lo, midPoint);
        }
        
        return midPoint;
    }

    private static <T> int partition(T[] array, int pivotIndex, int lo, int hi, Comparator<T> comparator){

        T pivot = array[pivotIndex];
        swapElement(array,lo,pivotIndex);
        int i = lo+1; //lo pointer
        int j = hi; //hi pointer


        while(true){ //main logic - walk i and j indices towards each other and make swaps to put values < pivot on left and values > pivot on right

            while (i <= j && comparator.compare(array[i], pivot) <= 0) //indices haven't crossed and the next index array[i] <= pivot
                i++;
            
            while(i<= j && comparator.compare(array[j], pivot) > 0)//indices havent crossed and array[j] > pivot
                j--;

            if(i >= j)
                break;
            
            swapElement(array, i, j);
        }

        swapElement(array, lo, j);
        return j; // split point

    }

    /**
     * helper method to handle element swapping
     * @param array - the array, i - the first element to be swapped, j the second element to be swapped
     */
    private static <T> void swapElement(T[] array, int i, int j){
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void insertionSort(int[] input){
        for(int i =1; i < input.length; i++){
            for(int j=i; j>0 && (input[j-1]>input[j]); j--){
                int temp = input[j];
                input[j]=input[j-1];
                input[j-1]=temp;
            }
        }   
    }

    /**
     * Standard merge sort method
     * @param array the inputted array to be sorted
     */
    public static <T extends Comparable<T>> void mergeSort(T[] array) {
        if(array.length <= 1)
            return;

        int mid = array.length /2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, array.length);

        mergeSort(left);
        mergeSort(right);

        merge(array,left,right);
    }

    /**
     * helper method for merge sort - merges the split subarrays
     * @param array the current subarray to be merged 
     * @param left the left sub array
     * @param right the  the right sub array
     */
    private static <T extends Comparable<T>> void merge(T[] array, T[] left, T[] right) {
        int leftPointer = 0;
        int rightPointer = 0;
        int i = 0;

        while(leftPointer < left.length || rightPointer < right.length){  //**** WE HAVE VALUES IN LEFT AND RIGHT)

            if(leftPointer >= left.length){ //edge case - left is at end, add right value
                array[i] = right[rightPointer];
                rightPointer++;
                i++;
            }
            else if (rightPointer >= right.length){ //edge case - right is at end, add left value
                array[i] = left[leftPointer];
                leftPointer++;
                i++;
            }

            else if (left[leftPointer].compareTo(right[rightPointer]) < 0) {
                array[i] = left[leftPointer];
                leftPointer++;
                i++;
            }
            else{
                array[i] = right[rightPointer];
                rightPointer++;
                i++;
            }
        }
    }

        /**
     * Comparator to comapre two Tweet objects by Id field -an int
     * @return positive int: tweet1 ID > tweet 2 ID, negative int: tweet 1 ID = tweet 2 ID
     */
    public static class CompareByID implements Comparator<Tweet>{

        /**
         * ovridden compare method based on ID
         */
        @Override
        public int compare(Tweet tweet1, Tweet tweet2){
            return tweet1.getId()-(tweet2.getId());
        }
    }

    /**
     * Comparator to compare two Tweet objects by PostDateTime Field - a DateTime object
     * @return -1,0,1
     */
    public static class ComparatorByTime implements Comparator<Tweet> {

        /**
         * overidden compare method based on date time
         */
        @Override
        public int compare(Tweet tweet1, Tweet tweet2){
            return tweet1.getPostDateTime().compareTo(tweet2.getPostDateTime());
        }
    }

    public static void printArray(int[] input){
        System.out.println(Arrays.toString(input));
    }
}
