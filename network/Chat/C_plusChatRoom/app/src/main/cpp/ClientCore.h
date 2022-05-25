//
// Created by eardh on 2021/12/25.
//

#ifndef HTTPSERVER_BOSS_H
#define HTTPSERVER_BOSS_H

#include <cwchar>
#include <mutex>
#include <thread>
#include <iostream>
#include <map>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <jni.h>
#include "LogUtil.h"

using namespace std;

static char DEFAULT_ADDRESS[10] = "127.0.0.1";

class ClientCore {
public :

    ClientCore(char *ip, int port, char *name);

    virtual ~ClientCore();

    int run();

    void receive();

    void prepareContext(JavaVM *g_jvm, jobject obj);

    void callJMethod(int code, char *msg);

    void exit_server();

    void sendMessage(char *msg);

private:
    char nick_name[20];
    int PORT;
    char *IP;
    bool quit;
    int client_socket;
    JavaVM *g_jvm;
    jobject obj;
};

#endif //HTTPSERVER_BOSS_H
