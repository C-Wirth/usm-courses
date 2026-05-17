Author: Colby Wirth

COS 280 Final Project - Visualizing Algorithms

Last Update: 13 November 2024

About: 
     This program visualizes and reports performances of graph traversal algorithms:  The following algorithms can be selected:  Dijkstra, A-Star with Euclidean Heuristic, A-Star with Manhattan Heuristic, and A-Star with Octile Heuristic
     The Graph is visualized in a two-dimensional topological grid space.

In this project you will find: 
    -README.md
    -Graph.java
    -GraphTraverser.java
    -VisualFrame.java
    -main.java

    How to run program:

     First compile ALL programs with the following command from its enclosing folder: 
     javac path/to/src/*

    There are two different manners to run the program:
    1. run  java path/to/main
        - this will build a 10x10 grid standard with a Dijkstra's algorithm to run

    2. run java path/to/main <algorithm> <heuristic> <number of nodes> <orthoginal distances> <diagonal distances> <random distances>

        i.   algorithm:  dijkstra OR aStar
        ii.  heuristic: null, euc, man, octile **NOTE** if dijkstra is selected, this input does not matter
        iii. number of nodes: an integer - use only 100 or 1000 - 1000000 only provides data - no visualization
        iv.  orthogonal distances - will be uniform.  an int. standard is 10
        v.   diagonal distances - will be uniform.  an int. standard is 14
        vi.  random distance either tue or false inputs **NOTE this will override inputs iv. and 

        Example run command: java main aStar euc 10000 10 14 false

    **NOTE** after each traversal, the Visual Frame must be closed, and the program run again

    Interfacing with the Graph:

        Left Clicking deletes/connects a node and all connected edges (turns it white)
        Right Clicking selects a starting or destination node for traversal

    Color Code:

        White: Null Value
        Black: Node
        Green: Start Node OR Shortest Path
        Red: Destination Node
        Yellow: Traversed Node - not shortest path
