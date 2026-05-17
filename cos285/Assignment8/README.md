Author: Colby Wirth

Assignment: Assignment 8

Course: COS 285 Data Structures

Instructor: Dr. Behrooz Mansouri

Last Update: 24 November 2024

In this project you will find: 
    
    -README.md
    -cos 285 assignment8.pdf
    -songpack
          MyDataReader.java
          MyHashMap.java
          MySentimentAnalysisModel.java
          program8.java
          Tweet.java

    How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac path/to/twitterpack/*

     After compiling ALL classes, run program8 class with the command: 
          java path/to/program8 <path/to/tweets_train.tsv> <path/to/tweets_test.tsv>

About: 
     This program trains a sentiment analysis model with custom Tweet objects.  Then it performs an analysis of the accuracy score.

Example Output:

200000 tweets added to train hash map
400000 tweets added to train hash map
600000 tweets added to train hash map
800000 tweets added to train hash map
1000000 tweets added to train hash map
1200000 tweets added to train hash map
1400000 tweets added to train hash map

 --- 

1551 milliseconds to build the train hash map
17 resizing to build the train hash map


 --- 

99 milliseconds to build the test hash map
13 resizing to build the test hash map

250000 elements added to the model
500000 elements added to the model
750000 elements added to the model
1000000 elements added to the model
1250000 elements added to the model
1500000 elements added to the model

 --- 

Ratio of correct predictions: 0.6273125

