//
// Created by eardh on 2021/12/25.
//

#include "Boss.h"
#include "Coder.h"

Boss::Boss(char *ip, int port) {
    IP = ip == nullptr ? DEFAULT_ADDRESS : ip;
    PORT = port;
    FD_ZERO(&conn_set);
    workers = new Worker[MAX_THREAD];
    quit = false;
    serverSocket = INVALID_SOCKET;
}

Boss::~Boss() {
    if (serverSocket != INVALID_SOCKET) {
        closesocket(serverSocket);
        WSACleanup();
    }
    quit = true;
    delete []workers;
}

int Boss::run() {
    int i = 0;
    WSADATA wsaData;
    int iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (iResult != NO_ERROR) {
        wprintf(L"WSAStartup failed with error: %ld\n", iResult);
        quit = true;
        return 1;
    }
    serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (serverSocket == INVALID_SOCKET) {
        wprintf(L"socket failed with error: %ld\n", WSAGetLastError());
        WSACleanup();
        quit = true;
        return 1;
    }
    sockaddr_in address{};
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr(IP);
    address.sin_port = htons(PORT);
    if (bind(serverSocket,
             (SOCKADDR *) &address, sizeof(address)) == SOCKET_ERROR) {
        wprintf(L"bind failed with error: %ld\n", WSAGetLastError());
        closesocket(serverSocket);
        WSACleanup();
        quit = true;
        return 1;
    }
    if (listen(serverSocket, 128) == SOCKET_ERROR) {
        wprintf(L"listen failed with error: %ld\n", WSAGetLastError());
        closesocket(serverSocket);
        WSACleanup();
        quit = true;
        return 1;
    }
    FD_SET(serverSocket, &conn_set);
    fd_set temp_set;
    while (true) {
        temp_set = conn_set;
        int keys = select(0, &temp_set, nullptr, nullptr, nullptr);
        if (keys > 0) {
            SOCKET socket = accept(serverSocket, nullptr, nullptr);
            if (socket != INVALID_SOCKET) {
                cout << "connect : " << socket << endl;
                i = (i + 1) % MAX_THREAD;
                thread t(&Worker::enroll, &workers[i], socket);
                t.detach();
            }
        }
        if (quit) {
            break;
        }
    }
}