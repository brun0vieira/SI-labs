/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.fct.unl;

/**
 *
 * @author Asus
 */
public class Warehouse {
    
native public void initializeHardwarePorts();    
//general use functions
native public void randomPosition();
native public void stopAll();
//int* getAllPositions();

//xx functions
native public void moveXRight();
native public void moveXLeft();
native public void stopXRight();
native public void stopXLeft();
native public void stopX();
native public int getXPosition();
//native public void gotoX(int x_dest);

//yy functions
native public void moveYIn();
native public void moveYOut();
native public void StopYIn();
native public void StopYOut();
native public void stopY();
native public int getYPosition();
//native public void gotoY(int y_dest);

//zz functions
native public void moveZUp();
native public void moveZDown();
native public void StopZUp();
native public void StopZDown();
native public void stopZ();
native public int getZPosition();
//native public void gotoZ(int z_dest);



//faltam implementar 
native public int getXMoving();
native public int getYMoving();
native public int getZMoving();


native public int getLeftStationMoving();
native public int getRightStationMoving();


native public boolean isAtZUp();
native public boolean isAtZDown();
native public boolean isPartInCage();
native public boolean isPartOnLeftStation();
native public boolean  isPartOnRightStation();


native public void moveLeftStationInside();
native public void moveLeftStationOutside();
native public void stopLeftLtation();
native public void moveRightStationInside();
native public void moveRightStationOutside();
native public void stopRightStation();
    
    
}
