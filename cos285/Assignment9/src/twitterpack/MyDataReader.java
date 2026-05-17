
package twitterpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class MyDataReader {
    //  Create a formatter with the specific date-time format
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    /**
     * This method takes in the string representation of dateTime and return LocalDate object
     * @param dateTimeString
     * @return
     */
    public static LocalDateTime dateConvert(String dateTimeString)
    {
        // Parse the string using the formatter
        LocalDateTime localDate;
        try {
            localDate = LocalDateTime.parse(dateTimeString, formatter);
          } catch (Exception e) {
            // Handle parsing exception, e.g., invalid format, invalid date
            System.err.println("Error parsing date-time string: " + e.getMessage());
            localDate = null;
          }
        return localDate;
    }
    
    public static HashMap<String, MyHeap<Tweet>> readDataToHashMapHeaps(String filePath) throws IOException
    {
        HashMap<String, MyHeap<Tweet>>  hashMapHeaps = new HashMap<String, MyHeap<Tweet>>();
        int counter=1;
        BufferedReader TSVReader = new BufferedReader(new FileReader(filePath));
        String line = TSVReader.readLine();
        while ((line = TSVReader.readLine()) != null) {   
            Tweet tweet = MyDataReader.lineToReport(line);
            MyHeap<Tweet> heap = hashMapHeaps.get(tweet.getUserID());
            if (heap == null) {
                heap = new MyHeap<Tweet> ();
                hashMapHeaps.put(tweet.getUserID(), heap);
            }
            heap.add(tweet);
          counter+=1;
          // using this to view progress
          if(counter%200000==0)
              System.out.println(counter + " records added");
        }
        return hashMapHeaps;
    }
    
    

    /**
     * Read the tweet file into a fixed size array
     * @param filePath file path to tweet file
     * @param tweetCount number of tweets in the file
     * @return unordered array of Tweet objects
     * @throws IOException
     */
    public static Tweet[] readDataToArray(String filePath, int tweetCount) throws IOException
    {
        Tweet[]tweets = new Tweet[tweetCount];
        int counter=0;
        BufferedReader TSVReader = new BufferedReader(new FileReader(filePath));
        String line = TSVReader.readLine();
        while ((line = TSVReader.readLine()) != null) {   
          Tweet tweet = MyDataReader.lineToReport(line);
          tweets[counter++] = tweet;
          // using this to view progress
          if(counter%20000==0)
              System.out.println(counter + " records added");
        }
        return tweets;
    }
    
    private static Tweet lineToReport(String inputLine) {
        // TODO Auto-generated method stub
        String[] items = inputLine.split("\t");
        int id = Integer.parseInt(items[0]);
        int temp = Integer.parseInt(items[1]);
        boolean sentiment = true;
        if(temp==0)
            sentiment=false;
        String userId = items[2];
        String text = items[3];
        LocalDateTime dateTime = dateConvert(items[4]);
        Tweet t = new Tweet(id, sentiment, userId, text, dateTime);
        return t;
    }
}
