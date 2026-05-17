package twitterpack;

import java.util.Scanner;
/**
 * Author: Colby Wirth 
 * Version: 30 November 2024 
 * Course: COS 285 
 * Class: program9.java
 */
public class UserInterface {

    MyHeap<Tweet> userHeap;

    public UserInterface(String id){

        if(program9.trainData == null)
            throw new IllegalStateException("Data not initialized");

        if(!program9.trainData.containsKey(id))
            throw new IllegalStateException("No ID found");

        userHeap = program9.trainData.get(id);
    }

    /**
     * main driver method for UI
     * @param args
     */
    public static void main(String[] args){

        System.out.println("\n---\n\nEnter user ID");
        
        try (Scanner scanner = new Scanner(System.in)) {
            String id = scanner.nextLine();
            UserInterface ui = new UserInterface(id);

            while(true){
                System.out.println("""
                                
                                ---
                                Choose action (enter a number 1-4):
                                1. View your most recent tweet
                                2. Delete your most recent tweet
                                3.View another user's tweet
                                4.Sign out
                                ---
                                """);

                String action = scanner.nextLine();

                switch(action){
                    case "1" -> {

                        if(ui.userHeap.isEmpty())
                            System.out.println("No tweets found.");
                        else
                            System.out.println(ui.userHeap.peek().toString());
                    }
                    case "2" -> {

                        if(ui.userHeap.isEmpty())
                            System.out.println("No tweets to delete.");

                        else{
                            ui.userHeap.delete();
                            System.out.println("Last Tweet successfully deleted.");
                        }
                    }
                    case "3" -> {

                        System.out.println("Enter the user ID that you want to view.");
                        String otherID = scanner.nextLine();
                        MyHeap<Tweet> otherUser = program9.trainData.get(otherID);
                        
                        if(otherUser == null)
                            System.out.println("User does not exist.");
                            
                        else if (otherUser.isEmpty())
                                System.out.println("This user has no tweets.");
                        else 
                            System.out.println(otherUser.peek().toString());
                    }
                    case "4" ->{

                        System.out.println("User Requested sign-out.");
                        return;
                    }
                    default ->{
                        System.out.println("Invalid option.  Try again");
                    }
                }
            }
        }    
    }
}