Author: Colby Wirth

Assignment: Assignment 9

Course: COS 285 Data Structures

Instructor: Dr. Behrooz Mansouri

Last Update: 30 November 2024

In this project you will find: 
    
    -README.md
    -cos 285 assignment9.pdf
    -twitterpack
          MyDataReader.java
          MyHeap.java
          program9.java
          Tweet.java
          UserInterface.java

    How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac path/to/twitterpack/*

     After compiling ALL classes, run program8 class with the command: 
          java path/to/program89<path/to/tweets_train.tsv>
     
     Then, select from the UI menu (enter a number 1-4)

About: 
     This prorgram builds a HashMap where the key is a username, and the value is a min-heap with all of the user's tweets ordered from most recent.

     The UserInterface.java class enables a user to interact with their tweets, and view other users tweets

Example Interaction:

200000 records added
400000 records added
600000 records added
800000 records added
1000000 records added
1200000 records added
1400000 records added
1969 milliseconds to build the Hashmap of Heaps

---

Enter user ID
_TheSpecialOne_

---
Choose action (enter a number 1-4):
1. View your most recent tweet
2. Delete your most recent tweet
3.View another user's tweet
4.Sign out
---

1
@switchfoot http://twitpic.com/2y1zl - Awww, that's a bummer. You shoulda got David Carr of Third Day to do it. ;D

---
Choose action (enter a number 1-4):
1. View your most recent tweet
2. Delete your most recent tweet
3.View another user's tweet
4.Sign out
---

2
Last Tweet successfully deleted.

---
Choose action (enter a number 1-4):
1. View your most recent tweet
2. Delete your most recent tweet
3.View another user's tweet
4.Sign out
---

3
Enter the user ID that you want to view.
scotthamilton
is upset that he can't update his Facebook by texting it... and might cry as a result School today also. Blah!

---
Choose action (enter a number 1-4):
1. View your most recent tweet
2. Delete your most recent tweet
3.View another user's tweet
4.Sign out
---

4
User Requested sign-out.