#pragma once
#ifndef _WAREHOUSE_H_
#define _WAREHOUSE_H_

#include <stdbool.h>

void initializeHardwarePorts();

//general use functions
void randomPosition();
void stopAll();
int* getAllPositions();

//xx functions
void moveXRight();
void moveXLeft();
void stopXRight();
void stopXLeft();
void stopX();
int getXPosition();
void gotoX(int x_dest);

//yy functions
void moveYIn();
void moveYOut();
void StopYIn();
void StopYOut();
void stopY();
int getYPosition();
void gotoY(int y_dest);

//zz functions
void moveZUp();
void moveZDown();
void StopZUp();
void StopZDown();
void stopZ();
int getZPosition();
void gotoZ(int z_dest);



//faltam implementar 
int getXMoving();
int getYMoving();
int getZMoving();


int getLeftStationMoving();
int getRightStationMoving();


bool isAtZUp();
bool isAtZDown();
bool isPartInCage();
bool isPartOnLeftStation();
bool isPartOnRightStation();


void moveLeftStationInside();
void moveLeftStationOutside();
void stopLeftLtation();
void moveRightStationInside();
void moveRightStationOutside();
void stopRightStation();

#endif /* _WAREHOUSE_H_ */