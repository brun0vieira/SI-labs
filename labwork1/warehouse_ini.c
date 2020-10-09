#include<jni.h>
#include<warehouse.h>



JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_initializeHardwarePorts(JNIEnv *env, jobject obj) {
	initializeHardwarePorts();
}

JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_randomPosition(JNIEnv *env, jobject obj) {
	randomPosition();
}

JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopAll(JNIEnv* env, jobject obj){
	stopAll();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveXRight(JNIEnv* env, jobject obj) {
	moveXRight();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveXLeft(JNIEnv* env, jobject obj) {
	moveXLeft();
}

JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopXRight(JNIEnv* env, jobject obj) {
	stopXRight();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopXLeft(JNIEnv* env, jobject obj) {
	stopXLeft();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopX(JNIEnv* env, jobject obj) {
	stopX();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getXPosition(JNIEnv* env, jobject obj){
	return getXPosition();
}



//JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_gotoX(JNIEnv* env, jobject obj) {
//	gotoX();
//}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveYIn(JNIEnv* env, jobject obj) {
	moveYIn();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveYOut(JNIEnv* env, jobject obj) {
	moveYOut();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_StopYIn(JNIEnv* env, jobject obj) {
	StopYOut();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_StopYOut(JNIEnv* env, jobject obj) {
	StopYOut();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopY(JNIEnv* env, jobject obj) {
	stopY();
}

JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getYPosition(JNIEnv* env, jobject obj) {
	return getYPosition();
}

//JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_gotoY(JNIEnv* env, jobject obj) {
//	gotoY();
//}

JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveZUp(JNIEnv* env, jobject obj) {
	moveZUp();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveZDown(JNIEnv* env, jobject obj) {
	moveZDown();
}

JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_StopZUp(JNIEnv* env, jobject obj) {
	StopZUp();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_StopZDown(JNIEnv* env, jobject obj) {
	StopZDown();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopZ(JNIEnv* env, jobject obj) {
	stopZ();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getZPosition(JNIEnv* env, jobject obj) {
	return getZPosition();
}


//JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_gotoZ(JNIEnv* env, jobject obj) {
//	gotoZ();
//}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getXMoving(JNIEnv* env, jobject obj) {
	return getXMoving();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getYMoving(JNIEnv* env, jobject obj) {
	return getYMoving();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getZMoving(JNIEnv* env, jobject obj) {
	return getZMoving();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getLeftStationMoving(JNIEnv* env, jobject obj) {
	return getLeftStationMoving();
}


JNIEXPORT jint JNICALL Java_si_fct_unl_Warehouse_getRightStationMoving(JNIEnv* env, jobject obj) {
	return getRightStationMoving();
}


JNIEXPORT jboolean JNICALL Java_si_fct_unl_Warehouse_isAtZUp(JNIEnv* env, jobject obj) {
	return isAtZup();
}


JNIEXPORT jboolean JNICALL Java_si_fct_unl_Warehouse_isAtZDown(JNIEnv* env, jobject obj) {
	return isAtZDown();
}


JNIEXPORT jboolean JNICALL Java_si_fct_unl_Warehouse_isPartInCage(JNIEnv* env, jobject obj) {
	return isPartInCage();
}


JNIEXPORT jboolean JNICALL Java_si_fct_unl_Warehouse_isPartOnLeftStation(JNIEnv* env, jobject obj) {
	return isPartOnLeftStation();
}


JNIEXPORT jboolean JNICALL Java_si_fct_unl_Warehouse_isPartOnRightStation(JNIEnv* env, jobject obj) {
	return isPartOnRightStation();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveLeftStationInside(JNIEnv* env, jobject obj) {
	moveLeftStationInside();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveLeftStationOutside(JNIEnv* env, jobject obj) {
	moveLeftStationOutside();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopLeftLtation(JNIEnv* env, jobject obj) {
	stopLeftLtation();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveRightStationInside(JNIEnv* env, jobject obj) {
	moveRightStationInside();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_moveRightStationOutside(JNIEnv* env, jobject obj) {
	moveRightStationOutside();
}


JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_stopRightStation(JNIEnv* env, jobject obj) {
	stopRightStation();
}