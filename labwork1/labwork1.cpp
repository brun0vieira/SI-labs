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
#include <time.h>

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

// **************************************** xx functions ****************************************
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

// **************************************** yy functions ****************************************

void moveYIn()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 3, 0);          // bit_3 a 0 
    setBitValue(&p, 4, 1);          // bit_4 a 1
    writeDigitalU8(4, p);
}

void moveYOut()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 4, 0);          // bit_4 a 0
    setBitValue(&p, 3, 1);          // bit_3 a 1
    writeDigitalU8(4, p);
}

void stopY() {
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 4, 0);          // bit_4 a 0
    setBitValue(&p, 3, 0);          // bit_3 a 0
    writeDigitalU8(4, p);
}

int getYPosition()
{
    int bb[2] = { 4,3 };
    int port;
    
    port = readDigitalU8(1);           //port1
    
    for (int i = 0; i < 2; i++) {
        if (getBitValue(port, bb[i]))  // active high
            return i + 1;
    }
    
    return(-1);
}

void gotoY(int y_dest) {
    int current = getYPosition();
    if (y_dest > current)
        moveYIn();
    else if (y_dest < current)
        moveYOut();
    //   while position not reached    
    while (getYPosition() != y_dest) {
        Sleep(1);
    }
    stopY();
}

// **************************************** zz functions ****************************************
void moveZUp()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 6, 0);          // bit_6 a 0
    setBitValue(&p, 5, 1);          // bit_5 a 1
    writeDigitalU8(4, p);
}

void moveZDown()
{
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 5, 0);          // bit_5 a 0
    setBitValue(&p, 6, 1);          // bit_6 a 1
    writeDigitalU8(4, p);
}

void stopZ() {
    uInt8 p = readDigitalU8(4);
    setBitValue(&p, 5, 0);          // bit_0 a 0
    setBitValue(&p, 6, 0);          // bit_1 a 0
    writeDigitalU8(4, p);
}

int getZPosition()
{
    //pp e bb fazem a correspondência dos bits com os ports
    int pp[5] = { 0,0,0,0,1 };
    int bb[5] = { 6,4,2,0,6 };
    int ports[2];

    ports[0] = readDigitalU8(2);            //port2
    ports[1] = readDigitalU8(1);            //port1

    for (int i = 0; i < 5; i++) {
        if (!getBitValue(ports[pp[i]], bb[i]))
            return i + 1;
    }

    return(-1);
}

void gotoZ(int z_dest) {
    int current = getZPosition();
    if (z_dest > current)
        moveZUp();
    else if (z_dest < current)
        moveZDown();
    //   while position not reached    
    while (getZPosition() != z_dest) {
        Sleep(1);
    }
    stopZ();
}

 // *********************************************************************************************

void randomPosition()
{
    srand(time(NULL));

    gotoX(rand() % 10 + 1);
    gotoZ(rand() % 5 + 1);
    gotoY(rand() % 2 + 1);
}

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

    randomPosition();


    while (tecla != 27) {

        //tecla = _getch();
        //if (tecla == 'd')
        //    moveXRight();
        //if (tecla == 'a')
        //    moveXLeft();
        //if (tecla == 's')
        //    stopX();
        
        
        //if (tecla == 'o')    
            //showStorageState(); // show every storage state
    }

    closeChannels();
    return 0;
}