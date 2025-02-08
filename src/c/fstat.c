#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include "fstat.h"

JNIEXPORT void JNICALL Java_aadesaed_cat_app_App_checkFdPath
  (JNIEnv *env, jobject obj_ptr, jint fd, jstring jpath)
{
    ino_t        out_ino, file_ino;
    dev_t        out_dev, file_dev;
    jclass       iio_exception;
    const char   *cpath;
    struct stat  out_statbuf, file_statbuf;

    if (fstat((int) fd, &out_statbuf) == -1)  {
        perror("fstat");
        exit(1);
    }

    cpath= (*env)->GetStringUTFChars(env, jpath, NULL);
    if (stat(cpath, &file_statbuf)) {
        perror("stat");
        exit(1);
    }

    out_dev = out_statbuf.st_dev ;
    out_ino = out_statbuf.st_ino;
    file_dev = file_statbuf.st_dev ;
    file_ino = file_statbuf.st_ino;

    if (sizeof(out_statbuf) != 0 && out_dev == file_dev && out_ino == file_ino) {
        iio_exception = (*env)->FindClass(env, "Laadesaed/cat/app/PurrcatException$InputIsOutput;");
		if (!iio_exception) {
			fprintf(stderr, "Could not create exception.\n");
			exit(EXIT_FAILURE);
		}

        (*env)->ThrowNew(env, iio_exception, NULL);
    }

    (*env)->ReleaseStringUTFChars(env, jpath, cpath);
}
