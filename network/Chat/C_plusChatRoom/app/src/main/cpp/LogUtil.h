//
// Created by eardh on 2021/12/30.
//

#ifndef C_PLUSCHATROOM_LOGUTIL_H
#define C_PLUSCHATROOM_LOGUTIL_H

#include <android/log.h>
#define Log_d(...) __android_log_print(ANDROID_LOG_DEBUG  , "9527", __VA_ARGS__)
#define Log_i(...) __android_log_print(ANDROID_LOG_INFO  , "9527", __VA_ARGS__)
#define Log_e(...) __android_log_print(ANDROID_LOG_ERROR  , "9527", __VA_ARGS__)

#endif //C_PLUSCHATROOM_LOGUTIL_H
