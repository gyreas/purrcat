#include "jni.h"
#include "jni_md.h"

#pragma once

JNIEXPORT void JNICALL Java_aadesaed_cat_input_Utils_checkFdPath
(JNIEnv *env, jobject obj_ptr, jint fd, jstring path);
