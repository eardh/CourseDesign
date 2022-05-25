//
// Created by eardh on 2021/12/25.
//

#ifndef HTTPSERVER_BOSS_H
#define HTTPSERVER_BOSS_H

#include <winsock2.h>
#include <cwchar>
#include <mutex>
#include <map>
#include <iostream>
#include "Coder.h"

using namespace std;

static string DEFAULT_ADDRESS = "0.0.0.0";

class ServerCore {
public :
    ServerCore(string IP, int port);
    virtual ~ServerCore();
    int run();
    void send_msg(SOCKET socket);
    void broadcast(SOCKET socket, const char *msg);

private:
    int PORT;
    string IP;
    fd_set conn_set;
    SOCKET serverSocket;
    bool quit;
    map<SOCKET, string> online_users;
};

#endif //HTTPSERVER_BOSS_H
