#pragma once
#ifndef _WAREHOUSE_H_
#define _WAREHOUSE_H_

#include <stdbool.h>



void initializeHardwarePorts();

int getXPosition();
int getYPosition();
int getZPosition();
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


void moveXRight();
void moveXLeft();
void stopXLeft();
void stopXRight();
void stopX();
void moveYInside();
void moveYOutside();
void StopYIn();
void StopYOut();
void stopY();
void moveZUp();
void moveZDown();
void StopZUp();
void StopZDown();
void stopZ();
void moveLeftStationInside();
void moveLeftStationOutside();
void stopLeftLtation();
void moveRightStationInside();
void moveRightStationOutside();
void stopRightStation();

int* getAllPositions();
void gotoX(int x_dest);
void gotoY(int y_dest);
void gotoZ(int z_dest);
void randomPosition();
void stopAll();

#endif
