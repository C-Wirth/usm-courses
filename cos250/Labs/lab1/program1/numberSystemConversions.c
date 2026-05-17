/**
 * 
 * Author: Colby Wirth
 * file: numberSystemConversions.c
 * Lab 1
 * Version: 3 February 2025
 * Course: COS 255
 * 
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <ctype.h>

#include "ioHandling.h"

//allocating memory for string or integer inputs
extern int inputNumber; //this is used if the user inputs an integer 
extern char inputString[33]; //this is used if the user inputs a binary or hexadecimal value.

#define INTEGER_SIZE 32
#define BINARY_BASE 2
#define HEX_SIZE 8
#define HEX_BASE 16
#define NIBBLE 4

//main functions
char* decToBin(int inputNum);
char* decToHex(int inputNum);
int binToDec(char* inputStr);
char* binToHex(char* inputStr);
int hexToDec(char* inputStr);
char* hexToBin(char* inputStr);

//helper functions
char intToHexMapper(int);
char hextoIntMapper(char);
void TwosComplement(char*);
void TwosComplementReverse(char* inputString, int length);
void convertBinaryByNibble(char inputString[], char finalValues[]);
void convertHexByNibble(char inputString[], char finalVals[]);

int main() {

    int executionStatus;
    executionStatus = inputChecker();

switch (executionStatus) { 

    case -1: //error handling 
        return simpleIOError();

    case 1: //decimal -> binary

       if (decimalInputGetter() == -1) 
            return simpleIOError(); 

        printf("\nUser inputted integer: %d \n", inputNumber);
        printf("The outputted String: %s \n", decToBin(inputNumber));
        
        break;

    case 2: //decimal -> hex

        if (decimalInputGetter() == -1)
            return simpleIOError(); 
        
        printf("\nUser inputted integer: %d \n", inputNumber);
        printf("The outputted String: %s \n", decToHex(inputNumber));
        break;

    case 3: //binary -> decimal

        if (binaryInputGetter() == -1) 
            return simpleIOError(); 

        printf("\nUser inputted string: %s \n", inputString);
        printf("The outputted integer: %d \n", binToDec(inputString));

        break;           

    case 4: //binary -> hex

        if (binaryInputGetter() == -1) 
            return simpleIOError(); 

        printf("\nUser inputted string: %s \n", inputString);
        printf("The outputted String: %s \n", binToHex(inputString));

        break;

    case 5: // hex-> decimal

        if (hexInputGetter() == -1)
            return simpleIOError();

        printf("\nUser inputted string: %s \n", inputString);
        printf("The outputted integer: %d \n", hexToDec(inputString));

        break;

    case 6: //hex -> binary

        if (hexInputGetter() == -1)
            return simpleIOError();

        printf("\nUser inputted string: %s \n", inputString);
        printf("The outputted String: %s \n", hexToBin(inputString));
        break;

    case 7: //quit
        break;
}
    return 0;
}

/**
 * The algorithm to convert an 32 bit signed integer to a binary value
 */
char* decToBin(int inputNum){

    int q = (inputNum < 0) ? -inputNum : inputNum;
    int r = 0;

    char* outputChars = (char*)malloc(INTEGER_SIZE + 1);
    outputChars[INTEGER_SIZE] = '\0';

    for(int i = INTEGER_SIZE-1; i >= 0 ; i--){  //start at 31st index

        if(q > 0){ //the quotient is not zero, keep dividing
            r = q%BINARY_BASE;
            q /=BINARY_BASE;
            outputChars[i] = r + '0';
        }
        else outputChars[i] = '0';
    }

    if(inputNum < 0)
        TwosComplement(outputChars);

    return outputChars;
}

/**
 * 
 * converts a 32 bit int value to hexadecimal value
 * 
 * First it converts the base 10 decimal to a binary, then binary to hexadecimal
 */
char* decToHex(int inputNum){

    char* binaries = (char*)malloc(INTEGER_SIZE+1);
    binaries[32] = '\0';
    strcpy(binaries, decToBin(inputNum));

    char* final_hex = (char*)malloc(HEX_SIZE+3);

    char* hexVals = binToHex(binaries);

    free(binaries);

    return hexVals;
}

/**
 * This function converts a string of binaries to base 10 decimal integer
 * It assumes the input follows twos complement convention
 */
int binToDec(char* inputStr){

    int binarySize = strlen(inputStr); 
    int isNegative = 0; 

    if (inputStr[0] == '1'){
        TwosComplementReverse(inputStr, binarySize); //handles twos complement with bin-> dec
        isNegative =1;
    }

    int result = 0;
    for(int i = binarySize-1, j =0 ; i >= 0 ; i --, j++){ //sum all digits: inputStr[i]*(2^i)

        result += (inputStr[i] - '0') * (int)pow(2, j);
    }

    if(isNegative ==1){
        result*=-1;
    }

    return result;
}

/**
* This function converts a string of binaries to 8 digit hexadecimal
* It assumes the input follows twos complement convention
*/
char* binToHex(char* inputStr){

    char* finalHex = (char*)malloc(HEX_SIZE + 3); // 8 digits for hex size, 3 for x,0 and terminating char

    int initVal = (inputStr[0] == '0') ? '0' : 'F'; 
    memset(finalHex+2, initVal, HEX_SIZE); //initialize the array with 0s or Fs    
    

    finalHex[0] = '0';
    finalHex[1] = 'x';
    finalHex[HEX_SIZE+2] = '\0';

    convertBinaryByNibble(inputStr, finalHex);

    return finalHex;
}

/**
* This function converts a string of hexadecimal digits to a 32 bit integer
* It assumes the input follows twos complement convention
* The input String must not have the 0x prefix
* The input String must be <= 8 digits
*/
int hexToDec(char* inputStr){

    char* binaries = hexToBin(inputStr);
    return binToDec(binaries);
}

/**
* This function converts a string of hexadecimal digits to a 32 digit binary string
* It assumes the input follows twos complement convention
* The input String must not have the 0x prefix
* The input String must be <= 8 digits
 */
char* hexToBin(char* inputStr){

    char* final_binaries = (char*)malloc(INTEGER_SIZE+1);
    final_binaries[INTEGER_SIZE] = '\0';


    int initVal = (inputStr[0] <= '7') ? '0' : '1'; 
    memset(final_binaries, initVal, INTEGER_SIZE - 1); //initialize the array with 1s or 0s    
        
    for(int i = 0 ; i < strlen(inputStr) ; i++){ //change all characters to to int representation: A->'10', '0'->'0' 
            inputStr[i] = hextoIntMapper(inputStr[i]);
        }

    convertHexByNibble(inputStr, final_binaries);

    return final_binaries;
}

/**
 * Helper function for hexToBin()
 * 
 * Converts a hex number to binary values by nibble (4 bits)
 * 
 */
void convertHexByNibble(char inputString[], char destiantionString[]){

    for(int i = strlen(inputString)-1, j = INTEGER_SIZE-1; i >= 0 ; i--, j-=NIBBLE){

            int q = inputString[i] - '0';

            for (int k = j ; k > j-4 ; k--){
                destiantionString[k] = (q % BINARY_BASE) + '0';
                q/= BINARY_BASE;
            }
    }
}

/**
 * Helper function for binToHex
 * 
 * Converts binary numbers to hex by 4 digits (nibble)
 * 
 */
void convertBinaryByNibble(char inputString[], char finalVals[]){

    int len = strlen(inputString); //the size of the input string (a factor of NIBBLE)
    int hexLen = (len / NIBBLE); //the size of the hexadecimal number before padding

    for (int i = len - 1, position = strlen(finalVals) -1 ; i >= 0; i -= 4, position--) { // position: the index of the output string, i: index of the input string

        int sumOf4 = 0;
        for (int j = i, k = 0; j > i-4; j--, k++) {
            sumOf4 += (inputString[j] - '0') * (int)pow(2, k); //of the form sum+= value * (2**chunkIndex)
        }
        finalVals[position] = intToHexMapper(sumOf4);
    }
}

/**
 * helepr function for hexToBin
 * Maps Characters A-F to ints
 */
char hextoIntMapper(char c){

    switch(c){

    case 'A':
        return 10 + '0';
    case 'B':
        return 11 + '0';
    case 'C':
        return 12 + '0';
    case 'D':
        return 13 + '0';
    case 'E':
        return 14 + '0';
    case 'F':
        return 15 + '0';
    default:
        return c;
    }
}

/**
 * helper function for convertBinaryByNibble
 * helper function to map vals to proper hex values
 */
char intToHexMapper(int val){
    
    switch (val)
    {
    case 10:
        return 'A';

    case 11:
        return 'B';
    
    case 12:
        return 'C';

    case 13:
        return 'D';

    case 14:
        return 'E';

    case 15:
        return 'F';
    
    default:
        return val + '0';
    }
}

/**
 * A standard twos complement used for converting negative ints to binary
 * 
 */
void TwosComplement(char* valueArray){

    for(int i = 0 ; i < INTEGER_SIZE ; i++){ //flip the bits
        valueArray[i] = (valueArray[i] == '1') ? '0' : '1';
    }

    //add 1 and carry
    for(int i = INTEGER_SIZE-1; i > 0 ; i--){

        if(valueArray[i] == '0'){
            valueArray[i] = '1';
            break;
        }
        else 
            valueArray[i] = '0' ;
    }
    valueArray[0] = '1';
}

/**
 * Helper function for binToDec
 * Flips a negative binary to positive
 */
void TwosComplementReverse(char* inputString, int length){

    for(int i = length -1 ; i >=0 ; i--){ //first step find the 1 to subtract

        if(inputString[i] == '1'){ //1 found, now flip all succeeding bits 
            for(int j = i ; j < length ; j++) //flipping succeeding bits
                inputString[j] = (inputString[j] == '1') ? '0' : '1';
            
            break;
        }
    }

    for(int i = 0 ; i < length ; i++) //flip all bits
        inputString[i] = (inputString[i] == '1') ? '0' : '1';
}