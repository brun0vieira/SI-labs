#include<jni_md.h>
#include<warehouse.h>



JNIEXPORT void JNICALL Java_si_fct_unl_Warehouse_initializeHardwarePorts(JNIEnv* env, jobject obj) {
	initializeHardwarePorts();
}
