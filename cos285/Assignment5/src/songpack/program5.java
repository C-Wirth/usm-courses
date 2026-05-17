package songpack;

import java.io.IOException;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;

/**
 * Author: Colby Wirth 
 * Version: 25 October 2024 
 * Course: COS 285 
 * Class: main.java
 */
public class program5 {

    static String filepath;
    static String tag;
    static final int MIN_SONGS = 1000;
    static final int MAX_SONGS = 10000;

    /**
    * Main method runs exercises for Assigment 5
    *Each exercise for the Assignment has its own method with its appropiate name
    * @throws IOException 
    * 
    */
    public static void main(String[] args) throws IOException{
        filepath = args[0];
        tag = args[1];

        BinarySearchTree bst = exercise1();
        exercise2(bst);
        exercise3(bst);
    }

    /**
     * Exercise 1
     * @return the BST created from song_lyrics.tsv
     * @throws IOException
     */
    public static BinarySearchTree exercise1() throws IOException{

        long startTime = System.currentTimeMillis();
        BinarySearchTree bst = MyDataReader.readFileToBST(filepath, tag);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(elapsedTime + " milliseconds to read the rock songs into a binary search tree\n");
        return bst;
    }

    /**
     * Exercise 2
     * @param bst the inputted bst from exercise 1
     */
    public static void exercise2(BinarySearchTree bst){

        long startTime = System.currentTimeMillis();
        ArrayList<Song> sortedSongs = bst.toSortedArrayList();
        StringBuilder topSongs = new StringBuilder("[");
        long elapsedTime = currentTimeMillis() - startTime;
        
        for(int i = sortedSongs.size()-1, j = 0 ; j < 10 ; i--, j++)
            topSongs.append(sortedSongs.get(i).getTitle()).append(",");

        topSongs.deleteCharAt(topSongs.length() - 1).append("]");

        System.out.println("Top-10 titles of songs with the highest number of views:\n" + topSongs + "\n");
        System.out.println(elapsedTime + " milliseconds to find top-10 popular songs\n");
    }

    /**
     * Exercise 3
     * @param bst the inputted bst from exercise 1
     */
    public static void exercise3(BinarySearchTree bst){

        long startTime = System.currentTimeMillis();
        BinarySearchTree bstClone = bst.clone();
        long elapsedTime = currentTimeMillis() - startTime;
        System.out.println(elapsedTime + " milliseconds to clone the tree\n");

        startTime = System.currentTimeMillis();
        bstClone.filterByView(MIN_SONGS, MAX_SONGS);
        boolean results = (bstClone.isValidBST(bstClone.root));
        elapsedTime = currentTimeMillis() - startTime;

        System.out.println("validation result of cloning and filtering: " + results + "\n");
        System.out.println(elapsedTime + " milliseconds to filter the tree and validate the binary search tree\n");

        startTime = System.currentTimeMillis();
        ArrayList<String> popularArtistsInClone = bstClone.popularArtist();
        elapsedTime = currentTimeMillis() - startTime;

        System.out.println("The Artist(s) with the highest view(s) in the given range: " + popularArtistsInClone + "\n");
        System.out.println(elapsedTime + " milliseconds to find popular artists\n");
    }
}
