#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#define INTEGER_SIZE 32
#define BINARY_BASE 2
#define HEX_SIZE 8
#define HEX_BASE 16
#define NIBBLE 4

int inputChecker();
int simpleIOError();
int decimalInputGetter();
int binaryInputGetter();
int hexInputGetter();


extern int inputNumber; //this is used if the user inputs an integer 
extern char inputString[INTEGER_SIZE+1]; //this is used if the user inputs a binary or hexidecimal value.


#endif
