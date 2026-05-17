#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "functions.h"

int inputNumber;
char inputString[33];

/**
 * Input checker handles the user I/O for selecting an algorithm
 * 
 * @return 1-6: the desired algoritm | 7: quit, -1: not an int or not in range.
 */
int inputChecker(){

    int input;

    printf("\nPlease input the integer that corresponds with your desired algorithm:\n"
    "1: decimal -> binary\n"
    "2: decimal -> hexidecimal\n"
    "3: binary -> decimal\n"
    "4: binary -> hexidecimal\n"
    "5: hexidecimal -> decimal\n"
    "6: hexidecimal -> binary\n"
    "7: quit\n\n"    
    );

    if (scanf("%d", &input) != 1 || input < 1 || input > 7) //the user did not input an integer or the int is not in range
        return -1;
    else // a valid entry
        return input;
}

    /**
     * A simple catch-all I/O error handler.
     */
    int simpleIOError(){
        
        fprintf(stderr, "\nThe user did not input a proper value, exiting.\n");
        return -1;
    }

 /**
 * This function checks a user's input for a 32 bit integer
 */
int decimalInputGetter(){

    printf("\nPlease enter a 32 bit integer.\n\n");

    if((scanf("%d", &inputNumber) != 1)) //The user did not input a proper 32 bit integer
        return -1;

    return 0;
}


/**
 * This function checks a user's input for a binary <= 32 bits
 */
int binaryInputGetter(){
    
    printf("\nPlease enter a binary number that follows the Two's Complement rules and is divisible by 4.\n\n");

    if ((scanf("%32s", inputString) != 1) || strlen(inputString) % 4 != 0) // The user did not input a proper string
        return -1;

    for(int i = 0 ; i < strlen(inputString) ; i ++){ //check for valid binary

        if(inputString[i] != '0' && inputString[i] != '1')
            return -1; //not valid binary
    }

    return 0;
}

/**
 * This function checks a user's input for a binary <= 32 bits
 */
int hexInputGetter(){
    
    printf("\nPlease enter a hexidecimal number formatted as 8 digits without the '0x' prefix,  that follows the 16's Complement rules\n");

    if ((scanf("%8s", inputString) != 1)) // The user did not input a proper string
        return -1;

    for(int i = 0 ; i < strlen(inputString) ; i ++){ //check for valid hex entry

            char c = inputString[i];

        if(!( (c >= 'A' && c <= 'F') || ( c >= '0' && c <= '9') ))
            return -1; //not valid hex entry
    }

    return 0;
}
