package songpack;

import java.io.IOException;
import java.util.Random;
/**
 * Author: Colby Wirth 
 * Version: 9 November 2024 
 * Course: COS 285 
 * Class: main.java
 */
public class program7 {

    static int search1 = -1; //out of bounds value
    static int search2 = 6421; // mean value
    static int search3 = 0; //minimum value
    static int search4 = 9247817; //max value
    static int search5 ;
                
    /**
    * Main method runs exercises for Assigment 7
    * @throws IOException 
    */
    public static void main(String[] args) throws IOException{
            
        //default for testing - no inputted parameters
        String filePath = "src/song_lyrics.tsv";
        String[] tags = {"pop", "rock", "rap", "rb", "misc", "country"};
        String tag = tags[0];

        //use inputted values instead of defaults
        if(args.length != 0){
            filePath = args[0];
            tag = args[1];
        }
                
        Random random = new Random(); // Use the random number generater to calculate the 5th search value
        search5 = random.nextInt(search4 - search3 + 1); //random value within min and max bounds
        int[] searchValues = {search1, search2, search3, search4, search5};

            AVLTree avl = task1(tag, filePath);
            task3(searchValues, avl);
            BinarySearchTree bst = task2(tag, filePath);
            task3(searchValues, bst);
        
    }

        /**
        * Carries out task 1 from assignment : analyze rotations, build time and height of an AVL Tree of songs for all tags
        * @throws IOException 
        */
        public static AVLTree task1(String tag, String filePath) throws IOException{

            long startTime = System.currentTimeMillis();
            AVLTree avl = MyDataReader.readFileToAVL(filePath, tag);
            long elapsedTime = System.currentTimeMillis() - startTime;

            System.out.println("Genre " + tag + "\n"
                                + avl.leftRotation + " left rotations\n"
                                + avl.rightRotation + " right rotations\n"
                                + avl.leftRightRotation + " left-right rotations\n"
                                + avl.rightLeftRotation + " right-left rotations\n"
                                + "The height of the tree is: " + avl.height(avl.root) + "\n"
                                + elapsedTime + " milliseconds to build the AVL tree for " + tag + " songs"
                                );
            return avl;
        }
    
        /**
        * Carries out task 2 from assignment : analyz height of a BST of songs for all tags
        * @throws IOException 
        */
        public static BinarySearchTree task2(String tag, String filePath) throws IOException{
    
                long startTime = System.currentTimeMillis();
                BinarySearchTree bst = MyDataReader.readFileToBST(filePath, tag);
                long elapsedTime = System.currentTimeMillis() - startTime;
    
                System.out.println(elapsedTime + " milliseconds to build the BST tree for " + tag + " songs\n"
                                               + "The height of the tree is: " + bst.height(bst.root)
                                               );
            return bst;
        }

        /**
        * Carries out task 3 from assignment : compare performance of AVL vs BST
        * @throws IOException 
        */
    public static void task3(int[] searches, BinarySearchTree tree) throws IOException{

        int i = 1;

        int average=0;

        for(int val : searches){ //conduct searches on the tree

            long startTime = System.nanoTime();
            tree.search(val);
            long elapsedTime = System.nanoTime() - startTime;

            if(tree.getClass() == BinarySearchTree.class && (i != 4 && i != 5)) 
                System.out.println(elapsedTime/1000 + " microseconds for search: " + i);
            
            else
                System.out.println(elapsedTime + " nanoseconds for search: " + i);

            i++;
            average+=elapsedTime;
        }
        System.out.println("Average time for a search in microseconds for a " + tree.getClass() + ": "  + average/5/1000);
    }
}