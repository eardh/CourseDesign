#include <jni.h>
#include <string>
#include "ClientCore.h"
#include "LogUtil.h"

ClientCore *boss = nullptr;
jobject obj_g;

extern "C"
JNIEXPORT void JNICALL
Java_com_eardh_chatroom_service_ChatService_working(JNIEnv *env,
                                                    jobject obj, jstring ip, jstring nickname) {
    char IP[16];
    char name[20];
    JavaVM *g_jvm = nullptr;
    strcpy(IP, env->GetStringUTFChars(ip, reinterpret_cast<jboolean *>(false)));
    strcpy(name, env->GetStringUTFChars(nickname, reinterpret_cast<jboolean *>(false)));
    boss = new ClientCore(IP, 9527, name);
    env->GetJavaVM(&g_jvm);
    obj_g = env->NewGlobalRef(obj);
    boss->prepareContext(g_jvm, obj_g);
    Log_i("0X%0x", env);
    boss->run();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_eardh_chatroom_service_ChatService_sendMessage(JNIEnv *env,jobject obj,
                                                        jstring content) {
    if (boss != nullptr) {
        char cont[4096]{0};
        strcpy(cont, env->GetStringUTFChars(content, reinterpret_cast<jboolean *>(false)));
        boss->sendMessage(cont);
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_eardh_chatroom_service_ChatService_exit_1server(JNIEnv *env, jobject obj) {
    if (boss != nullptr) {
        boss->exit_server();
    }
}