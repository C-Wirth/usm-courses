/**
 * 
 * Author: Colby Wirth
 * Assignemnt 1
 * Course: COS 255
 * 
 * 
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <ctype.h>

#include "functions.h"

//main functions
char* decToBin();
char* decToHex();
int binToDec();
char* binToHex();
int hexToDec();
char* hexToBin();

//helper functions
char intToHexMapper(int);
char hextoIntMapper(char);
void TwosComplement(char*);
void fifteensComplement(int*);
void fifteensComplementNumber2(char characters[]);

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

        decToBin();
        break;


    case 2: //decimal -> hex

      if (decimalInputGetter() == -1)
            return simpleIOError(); 
    
        decToHex();
        break;

    case 3: //binary -> decimal

        if (binaryInputGetter() == -1) 
            return simpleIOError(); 

        binToDec();
        break;
            

    case 4: //binary -> hex

        if (binaryInputGetter() == -1) 
            return simpleIOError(); 

        binToHex();
        break;

    case 5: // hex-> decimal

        if (hexInputGetter() == -1)
            return simpleIOError();

        hexToDec();
        break;

    case 6: //hex -> binary

        if (hexInputGetter() == -1)
            return simpleIOError();

        hexToBin();
        break;

    case 7: //quit
        break;
}
    return 0;
}

/**
 * The algorithm to convert an 32 bit integer to a binary value
 */
char* decToBin(){

    printf("input number: %d", inputNumber);

    int q = (inputNumber < 0) ? -inputNumber : inputNumber;
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

    if(inputNumber < 0)
        TwosComplement(outputChars);

    printf("\nThe original integer: %d \n"
            "The outputted binary: %s \n", inputNumber, outputChars
    );

    return outputChars;
}

/**
 * This function follows the pipeline dec -> bin -> hex as converting negative nuumbers is easily done with twos complement with binaries
 * 
 * converts a 32 bit int value to hexidecimal value
 */
char* decToHex(){

    printf("input num: %d\n", inputNumber);

    int isNegative = inputNumber < 0 ? 1 : 0;

    int q = (isNegative) ? -inputNumber : inputNumber;
    int r ;

    char* final_vals = (char*)calloc(HEX_SIZE + 3, 1);

    memset(final_vals + 2, isNegative ? 'F' : '0', HEX_SIZE);

    for(int i = HEX_SIZE + 1 ; i >=2 && q > 0 ; i--){
        final_vals[i] = intToHexMapper(q % HEX_BASE);
        q /= HEX_BASE;
    }

    printf("before complement hexidecimal value: %s \n", final_vals);

    if(isNegative){


    }
        // fifteensComplementNumber2(final_vals);

    final_vals[0] = '0';
    final_vals[1] = 'x';

    printf("final hexidecimal value: %s \n", final_vals);
    return final_vals;
}

int binToDec(){

    int binarySize = strlen(inputString); 
    int isNegative = 0; 

    if (inputString[0] == '1'){
        TwosComplementReverse(inputString, binarySize); //handles twos complement with bin-> dec
        isNegative =1;
    }

    int result = 0;
    for(int i = binarySize-1 ; i >= 0 ; i --){ //sum all digits: inputString[i]*(2^i)

        result += inputString[i] - '0' * (int)pow(2, binarySize - i - 1);
    }

    if(isNegative ==1){
        result*=-1;
    }

    printf("Final decimal value: %d", result);

    return result;
}

/**
*Converts a binary to 8 digit hexidecimal
*/
char* binToHex(){

    char* finalVals = (char*)malloc(HEX_SIZE + 3); // 8 digits for hex size, 3 for x,0 and terminating char

    for (int i = 2; i < HEX_SIZE + 3; i++)  //initialize finalVals with 1s of Fs depending on sign
         finalVals[i] = inputString[0] == '1' ? 'F': '0';

    finalVals[0] = '0';
    finalVals[1] = 'x';
    finalVals[HEX_SIZE+2] = '\0';

    convertBinaryByNibble(inputString, finalVals);

    printf("Final hexidecimal value: %s", finalVals);
    return finalVals;
}

int hexToDec(){

    int isNegative= (inputString[0] <= '7') ? 0 : 1;
    int len = strlen(inputString);

    if(inputString[0] > '7') //if the input is negative, flip values
        fifteensComplementNumber2(inputString);

    printf("STRING NOW: %s \n", inputString);

    int sum = 0;

    for(int i = len-1, j=0; i >= 0 ; i--, j++){ //convert base 16 to base 10

        int val = (inputString[i] < 'A') ? inputString[i] - '0' : (int) inputString[i] - 55;

        sum+=  val * (int) pow(HEX_BASE, j);
    }

    sum = (isNegative == 0 ? sum : -sum); //flip to negative if needed
    
    printf("Final decimal value: %d", sum);
    return sum;
}

char* hexToBin(inputStr []){

    char* finalVals = (char*)malloc(INTEGER_SIZE); // 8 digits for hex size, 3 for x,0 and terminating char

        for (int i = 0; i < INTEGER_SIZE -1 ; i++)  //initialize finalVals with 1s of 0s depending on sign
            finalVals[i] =  (inputString[0] <= '7') ? '0': '1';
        
        for(int i = 0 ; i < strlen(inputString) ; i++){ //change all characters to to int representation: A->'10', '0'->'0' 
                inputString[i] = hextoIntMapper(inputString[i]);
            }

        convertHexByNibble(inputString, finalVals);

        printf("Final binary value: %s", finalVals);
    return finalVals;
    
}

void fifteensComplementNumber2(char characters[]){

    int len = strlen(characters);

    for(int i = 0 ; i <  len; i++){

        char c = characters[i];
        int val;

        if(isdigit(c))
            val = 15 - (c - '0'); //val = between 15-6
        else{
            val = 15 - (c - 'A' + 10); // get the 15s complement of a Character: val = between 0-5
        }

        if (val < 10) {
            characters[i] = val + '0'; // convert back to 0-9
        } else {
            characters[i] = val - 10 + 'A'; // convert back to A-F
        }
    }
    
    for(int i = len-1 ; i >= 0 ; i--){ //add 1 and cary 

        if(characters[i] != 'F'){
            characters[i] +=1;

            for(int j = i+1 ; j < len ; j++){ //iterate back through the to the end of array
                characters[j] = '0';
            }
            break;
        }
    }
}

void convertHexByNibble(char inputString[], char finalVals[]){

    for(int i = strlen(inputString)-1, j = INTEGER_SIZE-1; i >= 0 ; i--, j-=NIBBLE){

            int q = inputString[i] - '0';

            for (int k = j ; k > j-4 ; k--){
                finalVals[k] = (q % BINARY_BASE) + '0';
                q/= BINARY_BASE;
            }
    }
}

void convertBinaryByNibble(char inputString[], char finalVals[]){

    int len = strlen(inputString); //the size of the input string (a factor of NIBBLE)
    int hexLen = (len / NIBBLE); //the size of the hexidecimal number before padding

    for (int i = len - 1, position = strlen(finalVals) -1 ; i >= 0; i -= 4, position--) {

        int sumOf4 = 0;
        for (int j = i, k = 0; j > i-4; j--, k++) {
            sumOf4 += (inputString[j] - '0') * (int)pow(2, k); //of the form sum+= value * (2**chunkIndex)
        }

        finalVals[position] = intToHexMapper(sumOf4);
    }
}


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
 * a standard 15' complement for converting negative ints to hexidecimal;
 */
void fifteensComplement(int* valueArray){

    for(int i = 0 ; i < HEX_SIZE ; i++){ //flip the bits
        valueArray[i] = HEX_BASE - 1 - (valueArray[i]);
    }

    //add 1 and cary
    valueArray[HEX_SIZE-1] +=1;
     for(int i = INTEGER_SIZE-1; i > 0 ; i--){

        if(valueArray[i] == HEX_BASE){
            valueArray[i]=0;
            valueArray[i-1]+=1;
        }
    }
}

/**
 * Flips a negative binary to positive
 * 
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