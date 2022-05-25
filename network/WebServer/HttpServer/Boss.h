//
// Created by eardh on 2021/12/25.
//

#ifndef HTTPSERVER_BOSS_H
#define HTTPSERVER_BOSS_H

#include <winsock2.h>
#include <cwchar>
#include "Worker.h"

static int MAX_THREAD = 4;

static char DEFAULT_ADDRESS[10] = "0.0.0.0";

class Boss {
public :
    Boss(char *ip, int port);
    virtual ~Boss();
    int run();

private:
    int PORT;
    char *IP;
    fd_set conn_set;
    SOCKET serverSocket;
    Worker *workers;
    bool quit;
};

#endif //HTTPSERVER_BOSS_H
