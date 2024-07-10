#include <jni.h>
#include <stdio.h>
#include "dev_neelesh_jni_JNIDemo.h"

// Implementation of the native method
JNIEXPORT void JNICALL Java_dev_neelesh_jni_JNIDemo_sayHello(JNIEnv *env, jobject obj) {
    printf("Hello viewers - This method is written in C language!\n");
}
