package twitterpack;

import java.io.IOException;
import java.util.HashMap;

/**
 * Author: Colby Wirth 
 * Version: 30 November 2024 
 * Course: COS 285 
 * Class: program9.java
 */
public class program9 {

    //default for testing - no inputted paths with args
    static String pathToTrainSet = "src/data/tweets_train.tsv";
    static String pathToTestSet = "src/data/tweets_test.tsv";

    static HashMap<String, MyHeap<Tweet>> trainData;
                    
        /**
        * Main method runs exercises for Assigment 8
        * @throws IOException 
        */
        public static void main(String[] args) throws IOException{
    
            checkPath(args);
            trainData = mapBuilder(pathToTrainSet);
        
        UserInterface.main(null);
    }
    
        /**
         * check the path
         * @param args from main method
         */
        private static void checkPath(String[] args){
    
            if(args.length == 1){
                pathToTrainSet = args[0];
            }
        }
        
        /**
         * method to handle the outputs specified in the assignment
         * @param path the path to a dataset
         * @return the built HashMap object - Key: String, user ID | Value : MyHeap<Tweet>,
         *           a heap with all tweets organzied in ascending order by data
         * @throws IOException
         */
        public static HashMap<String, MyHeap<Tweet>>mapBuilder(String path) throws IOException{

            long startTime = System.currentTimeMillis();
            HashMap<String, MyHeap<Tweet>>map = MyDataReader.readDataToHashMapHeaps(path);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            System.out.println(elapsedTime + " milliseconds to build the Hashmap of Heaps");

            return map;
        }
}