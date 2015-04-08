#include <com_example_rg_androidstudiodemo_jni_MathKit.h>

JNIEXPORT jint JNICALL Java_com_example_rg_androidstudiodemo_jni_MathKit_square
  (JNIEnv *env, jclass cls, jint num)
  {
        return num*num;
  }