//
// Created by eardh on 2021/12/25.
//

#include "ClientCore.h"

ClientCore::ClientCore(char *ip, int port, char *name) {
    IP = ip == nullptr ? DEFAULT_ADDRESS : ip;
    PORT = port;
    quit = false;
    strcpy(nick_name, name);
    client_socket = -1;
}

ClientCore::~ClientCore() {
    if (client_socket != -1) {
        close(client_socket);
    }
    quit = true;
}

int ClientCore::run() {
    client_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (client_socket == -1) {
        callJMethod(-1, nullptr);
        return 1;
    }
    sockaddr_in address{};
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr(IP);
    address.sin_port = htons(PORT);
    int iResult = connect(client_socket, (struct sockaddr*)&address, sizeof(address));;
    if (iResult == -1) {
        callJMethod(-1, nullptr);
        close(client_socket);
        return 1;
    }
    callJMethod(1, nullptr);
    thread t(&ClientCore::receive, this);
    t.detach();
    int l = send(client_socket, nick_name, strlen(nick_name), 0);
    if (l < 0) {
        callJMethod(4, nullptr);
        close(client_socket);
        return 1;
    }
    return 0;
}

void ClientCore::receive() {
    char buff[4096];
    while(true) {
        memset(buff, 0, 4096);
        int l = recv(client_socket, buff, 4096, 0);
        if (l > 0) {
            callJMethod(2, buff);
        } else {
            if (!quit) {
                callJMethod(3, nullptr);
            }
            break;
        }
    }
    g_jvm->DetachCurrentThread();
}

void ClientCore::prepareContext(JavaVM *g_jvm, jobject obj) {
    this->g_jvm = g_jvm;
    this->obj = obj;
}

void ClientCore::callJMethod(int code, char *msg) {
    JNIEnv *env;
    if(g_jvm->AttachCurrentThread(&env, nullptr) != JNI_OK){
        return;
    }
    g_jvm->GetEnv((void**) &env, JNI_VERSION_1_6);
    if(env == nullptr){
        return;
    }
    Log_i("0X%0x", env);
    jclass jcls = env->GetObjectClass(obj);
    if(nullptr != jcls){
        jmethodID callback = env->GetMethodID(jcls,"callback", "(ILjava/lang/String;)V");
        if(nullptr != callback){
            jstring rest = nullptr;
            if (msg != nullptr) {
                rest = env->NewStringUTF(msg);
            }
            env->CallVoidMethod(obj, callback, code, rest);
        }
    }
}

void ClientCore::exit_server() {
    if (client_socket != -1) {
        quit = true;
        shutdown(client_socket, 2);
        client_socket = -1;
    }
}

void ClientCore::sendMessage(char *msg) {
    if (client_socket != -1) {
        int l = send(client_socket, msg, strlen(msg), 0);
        if (l > 0) {
            callJMethod(6, nullptr);
        } else {
            callJMethod(4, nullptr);
            exit_server();
        }
    }
}



