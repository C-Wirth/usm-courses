Author: Colby Wirth

Assignment: Assignment 7

Course: COS 285 Data Structures

Instructor: Dr. Behrooz Mansouri

Last Update: 12 November 2024

In this project you will find: 
    
    -README.md
    -assignment7.pdf
    -metrics.py
    
    -songpack
          AVLTtree.java
          BinarySearchTree.java
          MySearchEngine.java
          Song.java
          program7.java

    How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac path/to/songpack/*

     After compiling ALL classes, run program5 class with the command: 
          java path/to/songpack/program5 <genre>

About: 
     This program takes a file of Songs and a specified genre type and executes the main method in program7.java

Example Output with 'pop' genre

480000 records added
Genre pop
113279 left rotations
1468 right rotations
8654 left-right rotations
1787 right-left rotations
The height of the tree is: 19
4421 milliseconds to build the AVL tree for pop songs
93833 nanoseconds for search: 1
5000 nanoseconds for search: 2
250 nanoseconds for search: 3
125 nanoseconds for search: 4
84 nanoseconds for search: 5
Average time for a search in microseconds for a class songpack.AVLTree: 19
480000 records added
4286 milliseconds to build the BST tree for pop songs
The height of the tree is: 499
8509 microseconds for search: 1
516 microseconds for search: 2
8762 microseconds for search: 3
958 nanoseconds for search: 4
167 nanoseconds for search: 5
Average time for a search in microseconds for a class songpack.BinarySearchTree: 3557

