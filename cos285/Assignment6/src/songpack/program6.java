package songpack;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Colby Wirth
 * Version 5 November 2024
 * Dr. Berhrooz Mansouri
 * program6
 */
public class program6 {

    public static final String QUERY1 = "we are the champions";
    public static final String QUERY2 = "i will always love you";
    public static final String QUERY3 = "walking on sunshine";
    public static final String QUERY4 = "dancing in the rain";
    public static final String QUERY5 = "put your hands in the jupitersky";
        
    public static String filePath="src/song_lyrics.tsv";
    public static  String genre = "rock";

    public static MySearchEngine engine ;
    
        /**
         * main method builds the index and makes queries
         * @param args args[0] filepath/to/song_lyrics.tsv args[1] genre
         * @throws IOException
         */
        public static void main(String[] args) throws IOException{
    
            if(args.length != 0){
                filePath = args[0];
            }
            if(args.length == 2){
                genre = args[1];
            }

            ArrayList<Song> songs = MyDataReader.readFileToArrayList(filePath, genre);
            // overloaded method to build the searchengine with ALL songs from song_lyrics.tsv
            // ArrayList<Song> songs = MyDataReader.readFileToArrayList(filePath); 

            //building the index
            long startTime = System.currentTimeMillis();
            engine = new MySearchEngine(songs);
            long endtime = System.currentTimeMillis() - startTime;
            System.out.println("\n" + endtime + " milliseconds to build the index");
            
            songs=null; //attempting to free from heap space
            System.gc();

            queryMaker(QUERY1);
            queryMaker(QUERY2);
            queryMaker(QUERY3);
            queryMaker(QUERY4);
            queryMaker(QUERY5);
        }

    /**
     * helper method to execute queries and output times
     * @param query the query to make
     */
    private static void queryMaker(String query){
        long startTime = System.currentTimeMillis();
        engine.search(query);
        long endtime = System.currentTimeMillis() - startTime;
        System.out.println(endtime + " milliseconds to search for " + query);
    }
}
