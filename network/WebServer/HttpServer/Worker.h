//
// Created by eardh on 2021/12/26.
//

#ifndef HTTPSERVER_WORKER_H
#define HTTPSERVER_WORKER_H

#include <winsock2.h>
#include <mutex>
#include <thread>
#include "HttpRequest.h"
#include "HttpResponse.h"

using namespace std;

class Worker {
public:
    Worker();
    void enroll(SOCKET socket);
    void run();
    void deal(HttpRequest request, SOCKET socket);

private:
    fd_set read_set;
    mutex init_mutex;
    thread work_thread;
    bool already_start;
};


#endif //HTTPSERVER_WORKER_H
