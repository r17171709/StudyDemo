#include <jni.h>
#include <string>
#include "MD5.h"

/**
 * 字符串拼接方法
 * https://blog.csdn.net/cloverjf/article/details/78851988
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_house365_rent_RentAuth_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jstring token,
        jstring auth_time,
        jstring auth_version) {
    std::string hello = "Hello from C++";

//    const char *originStr;
//    //将jstring转化成char *类型
//    originStr = env->GetStringUTFChars(str, JNI_FALSE);
//
////    char *sh = const_cast<char *>(originStr);
//
//    char* c = (char*)malloc(strlen(originStr) + strlen(originStr) + 1);
//    strcpy(c, originStr);
//    strcat(c, originStr);
//
//    MD5 md5 = MD5(c);
//    std::string md5Result = md5.hexdigest();
//
//    return env->NewStringUTF(md5Result.c_str());


    jstring vcode = env->NewStringUTF(
            "AAAAB3NzaC1yc2EAAAADAQABAAABAQDNeYnErQ4Jf/h+OK+az2WXGFRnod2j6TGmzl8LsUnqcnta3ZgvF/R/VV");
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat",
                                                 "(Ljava/lang/String;)Ljava/lang/String;");
    jobject str1 = env->CallObjectMethod(token, concat_methodID, auth_time);
    jobject str2 = env->CallObjectMethod((jstring) str1, concat_methodID, auth_version);
    jobject str3 = env->CallObjectMethod((jstring) str2, concat_methodID, vcode);
    const char *cResult = env->GetStringUTFChars((jstring) str3, JNI_FALSE);
    //释放内存
    env->DeleteLocalRef(vcode);
    env->ReleaseStringUTFChars((jstring) str3, cResult);


//    const char *str1 = env->GetStringUTFChars(token, JNI_FALSE);
//    const char *str2 = env->GetStringUTFChars(auth_time, JNI_FALSE);
//    const char *str3 = env->GetStringUTFChars(auth_version, JNI_FALSE);
//    jstring vcode = env->NewStringUTF("AAAAB3NzaC1yc2EAAAADAQABAAABAQDNeYnErQ4Jf/h+OK+az2WXGFRnod2j6TGmzl8LsUnqcnta3ZgvF/R/VV");
//    const char *str4 = env->GetStringUTFChars(vcode, JNI_FALSE);
//
//    char *c = (char *) malloc(strlen(str1) + strlen(str2) + 1);
//    strcpy(c, str1);
//    strcat(c, str2);
//
//    char *c1 = (char *) malloc(strlen(str3) + strlen(str4) + 1);
//    strcpy(c1, str3);
//    strcat(c1, str4);
//
//    char *cResult = (char *) malloc(strlen(c) + strlen(c1) + 1);
//    strcpy(cResult, c);
//    strcat(cResult, c1);
//
//    env->DeleteLocalRef(vcode);
//    env->ReleaseStringUTFChars(token, str1);
//    env->ReleaseStringUTFChars(auth_time, str2);
//    env->ReleaseStringUTFChars(auth_version, str3);

    MD5 md5 = MD5(cResult);
    std::string md5Result = md5.hexdigest();
    return env->NewStringUTF(md5Result.c_str());
}