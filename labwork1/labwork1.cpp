// labwork1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

//http://localhost:8081/ss.html


#include <iostream>

#include <stdio.h>
#include <conio.h>

#include <stdio.h>
#include <conio.h>
#include <stdio.h>
#include <windows.h>


extern "C" { 
    // observe your project contents.  We are mixing C files with cpp ones.
    // Therefore, inside cpp files, we need to tell which functions are written in C.
    // That is why we use extern "C"  directive
#include <interface.h>
}

void setBitValue(uInt8* variable, int n_bit, int new_value_bit)
// given a byte value, set the n bit to value
{
    uInt8  mask_on = (uInt8)(1 << n_bit);
    uInt8  mask_off = ~mask_on;
    if (new_value_bit)  *variable |= mask_on;
    else                *variable &= mask_off;
}
int getBitValue(uInt8 value, uInt8 n_bit)
// given a byte value, returns the value of bit n
{
    return(value & (1 << n_bit));
}

// **************************************** xx fucntions ****************************************
void moveXRight()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 1, 0);          // bit_1 a 0
    setBitValue(&p, 0, 1);          // bit_0 a 1
    writeDigitalU8(4, p);
}

void moveXLeft()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 0, 0);          // bit_0 a 0
    setBitValue(&p, 1, 1);          // bit_1 a 1
    writeDigitalU8(4, p);
}

void stopX() {
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 0, 0);          // bit_0 a 0
    setBitValue(&p, 1, 0);          // bit_1 a 0
    writeDigitalU8(4, p);
}

int getXPosition()
{
    //pp e bb fazem a correspondência dos bits com os ports
    int pp[10] = { 0,0,0,0,0,0,0,0,1,1 };   
    int bb[10] = { 0,1,2,3,4,5,6,7,0,1 };
    int ports[2];
    
    ports[0] = readDigitalU8(0);            //port0
    ports[1] = readDigitalU8(1);            //port1
    
    for (int i = 0; i < 10; i++) {
        if (!getBitValue(ports[pp[i]], bb[i]))
            return i + 1;
    }
    
    return(-1);
}

void gotoX(int x_dest) {
    int current = getXPosition();
    if (x_dest > current)
        moveXRight();
    else if (x_dest < current)
        moveXLeft();
    //   while position not reached    
    while (getXPosition() != x_dest) {
        Sleep(1);
    }
    stopX();
}

// **************************************** yy fucntions ****************************************

// **************************************** zz fucntions ****************************************


int main()
{
    printf("\ngo to browser and open address: http://localhost:8081/ss.html");

    // INPUT PORTS
    createDigitalInput(0);
    createDigitalInput(1);
    createDigitalInput(2);
    createDigitalInput(3);
    // OUTPUT PORTS
    createDigitalOutput(4);
    createDigitalOutput(5);

    writeDigitalU8(4, 0);
    writeDigitalU8(5, 0);

    printf("\ncallibrate kit manually and press    enter...");

    int tecla = 0;

    while (tecla != 27) {

        //tecla = _getch();
        //if (tecla == 'd')
        //    moveXRight();
        //if (tecla == 'a')
        //    moveXLeft();
        //if (tecla == 's')
        //    stopX();
        
        gotoX(10);

        //if (tecla == 'o')    
            //showStorageState(); // show every storage state
    }

    closeChannels();
    return 0;
}