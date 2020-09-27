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

int main()
{
    printf("Welcome to Inteligent Suprvision\n");
    printf("press key");

    createDigitalInput(0);
    createDigitalInput(1);
    createDigitalInput(2);
    createDigitalInput(3);
    createDigitalOutput(4);
    createDigitalOutput(5);

    writeDigitalU8(4, 0x21);

    Sleep(10000);


    writeDigitalU8(4, 0x00);
    closeChannels();
    return 0;
}