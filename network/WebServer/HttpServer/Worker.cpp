//
// Created by eardh on 2021/12/26.
//

#include <iostream>
#include <fstream>
#include "Worker.h"
#include "Coder.h"

Worker::Worker() {
    FD_ZERO(&read_set);
    already_start = false;
}

void Worker::enroll(SOCKET socket) {
    init_mutex.lock();
    if (!already_start) {
        already_start = true;
        work_thread = thread(&Worker::run, this);
        work_thread.detach();
    }
    FD_SET(socket, &read_set);
    init_mutex.unlock();
}

void Worker::run() {
    char buff[1024];
    TIMEVAL tv;
    tv.tv_sec = 2;
    tv.tv_usec = 2;
    fd_set temp_set;
    while(read_set.fd_count > 0) {
        temp_set = read_set;
        int keys = select(0, &temp_set, nullptr, nullptr, &tv);
        if (keys > 0) {
            for (int i = 0; i < temp_set.fd_count; ++i) {
                if (FD_ISSET(temp_set.fd_array[i], &temp_set)) {
                    memset(buff, 0, 1024);
                    int l = recv(temp_set.fd_array[i], buff, 1024, 0);
                    if (l > 0) {
                        HttpRequest request = HttpRequest::resolve(buff);
                        deal(request, temp_set.fd_array[i]);
                    }
                    else {
                        cout << "close : " << temp_set.fd_array[i] << endl;
                        init_mutex.lock();
                        FD_CLR(temp_set.fd_array[i], &read_set);
                        init_mutex.unlock();
                        closesocket(temp_set.fd_array[i]);
                    }
                }
            }
        }
    }
    init_mutex.lock();
    FD_ZERO(&read_set);
    FD_ZERO(&temp_set);
    already_start = false;
    init_mutex.unlock();
}

void Worker::deal(HttpRequest request, SOCKET socket) {
    char pl[128] = "static";
    strcat(pl, request.getUrl());
    cout << socket << " " <<  pl << endl;
    ifstream stm(pl, ios::in | ios::binary);
    HttpResponse response;
    if (!stm.is_open()) {
        response = HttpResponse::error();
    } else {
        stm.seekg(0, ios::end);
        long len = stm.tellg();
        response = HttpResponse::ok(pl);
        response.setContentLength(len);
        stm.seekg(0, ios::beg);
    }
    char msg[1024]{0};
    HttpResponse::encode(response, msg);
    send(socket, msg, strlen(msg), 0);
    char buff[4096];
    while (!stm.eof()) {
        memset(buff, 0, 4096);
        stm.read(buff, 4096);
        if (stm.gcount() == 0) {
            break;
        }
        send(socket, buff, stm.gcount(), 0);
    }
    stm.close();
}


