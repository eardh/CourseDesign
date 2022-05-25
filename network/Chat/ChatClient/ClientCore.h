//
// Created by eardh on 2021/12/25.
//

#ifndef HTTPSERVER_BOSS_H
#define HTTPSERVER_BOSS_H

#include <winsock2.h>
#include <cwchar>
#include <mutex>
#include <thread>
#include <iostream>
#include "Coder.h"
#include <map>

using namespace std;

static string DEFAULT_ADDRESS = "127.0.0.1";

class ClientCore {
public :

    ClientCore(const string& ip, int port, string name);

    virtual ~ClientCore();

    int run();

    void receive();

private:
    string nick_name;
    int PORT;
    string IP;
    bool quit;
    SOCKET client_socket;
};

#endif //HTTPSERVER_BOSS_H
