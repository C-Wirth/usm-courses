Author: Colby Wirth

Assignment: Assignment 10

Course: COS 285 Data Structures

Instructor: Dr. Behrooz Mansouri

Last Update: 10 December 2024

In this project you will find: 
    
    -README.md
    -cos 285 assignment10.pdf
    -tweetPack
          MyDataReader.java
          MySorts.java
          progra10.java
          Tweet.java

    How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac path/to/twitterpack/*

     After compiling ALL classes, run program10 class with the command: 
          java path/to/program10 <path/to/tweets_test.tsv>

     Example run command: java bin/program10 bin/tweets_test.tsv

About: 
     The main class, program 10 runs three different analysis functions - each with their own specified tasks for gathering data regarding run-times for each sorting function 

Example Output:

70 milliseconds for quicksort 1 based on date time
45 milliseconds for quicksort 2 based on date time

74 milliseconds for quicksort based on tweet ID 
Top 10 Results:
ID: 2000000 --- date: 2009-06-16T05:24:09
ID: 2000001 --- date: 2009-06-15T20:14:49
ID: 2000002 --- date: 2009-06-24T22:49:40
ID: 2000003 --- date: 2009-06-23T14:56:27
ID: 2000004 --- date: 2009-06-23T10:18:16
ID: 2000005 --- date: 2009-06-25T04:47:18
ID: 2000006 --- date: 2009-06-23T16:48:36
ID: 2000007 --- date: 2009-06-16T05:14:02
ID: 2000008 --- date: 2009-06-16T03:11:21
ID: 2000009 --- date: 2009-06-15T22:27:19

18 milliseconds for quicksort based on tweet ID
29 milliseconds for radixsort based on tweet ID