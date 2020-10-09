// labwork1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

//http://localhost:8081/ss.html


#include <iostream>
#include <thread>

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
//#include <warehouse.h>
}




//void checkLimits() 
//{
//    int x, y, z;
//    int * position;
//    
//    while (1) {
//
//        position = getAllPositions();
//        x = position[0]; y = position[1]; z = position[2];
//
//        if (x == 1)
//            stopXLeft();
//
//        if (x == 10)
//            stopXRight();
//
//        if (y == 2)
//            StopYIn();
//
//        if (y == 1)
//            StopYOut();
//
//        if (z == 1)
//            StopZDown();
//
//        if (z == 5)
//            StopZUp();
//
//        Sleep(100);
//
//        printf("y=%d", y);
//    }
//
//}



int main()
{
    printf("\ngo to browser and open address: http://localhost:8081/ss.html");

    //initializeHardwarePorts();

    char tecla = 0;

    //randomPosition();

    //std::thread first(checkLimits);
    std::cout << "\n\nmain and checkLimits are now executing concurrently...\n";

    while (tecla != '0') {

        printf("entrou aqui");

        //switch (tecla) {
        //case 'd': moveXRight(); break;
        //case 'a': moveXLeft(); break;
        //case 'w': moveZUp(); break;
        //case 's': moveZDown(); break;
        //case 'i': moveYIn(); break;
        //case 'o': moveYOut(); break;
        //case 'x': stopAll(); 
        //}

        //if (tecla == 'o')    
            //showstoragestate(); // show every storage state
    }

    closeChannels();
    return 0;
}