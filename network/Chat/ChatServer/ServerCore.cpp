//
// Created by eardh on 2021/12/25.
//

#include "ServerCore.h"

ServerCore::ServerCore(string ip, int port) {
    IP = ip.empty() ? DEFAULT_ADDRESS : ip;
    PORT = port;
    FD_ZERO(&conn_set);
    quit = false;
    serverSocket = INVALID_SOCKET;
}

ServerCore::~ServerCore() {
    if (serverSocket != INVALID_SOCKET) {
        closesocket(serverSocket);
        WSACleanup();
    }
    quit = true;
}

int ServerCore::run() {
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
    address.sin_addr.s_addr = inet_addr(IP.c_str());
    address.sin_port = htons(PORT);
    if (bind(serverSocket,
             (SOCKADDR *) &address, sizeof(address)) == SOCKET_ERROR) {
        wprintf(L"bind failed with error: %ld\n", WSAGetLastError());
        closesocket(serverSocket);
        WSACleanup();
        quit = true;
        return 1;
    }
    if (listen(serverSocket, 64) == SOCKET_ERROR) {
        wprintf(L"listen failed with error: %ld\n", WSAGetLastError());
        closesocket(serverSocket);
        WSACleanup();
        quit = true;
        return 1;
    }
    FD_SET(serverSocket, &conn_set);
    fd_set temp_set;
    FD_ZERO(&temp_set);
    char buff1[20];
    while (true) {
        temp_set = conn_set;
        int keys = select(0, &temp_set, nullptr, nullptr, nullptr);
        if (keys > 0) {
            for (int i = 0; i < temp_set.fd_count; ++i) {
                if (temp_set.fd_array[i] == serverSocket) {
                    SOCKET socket = accept(serverSocket, nullptr, nullptr);
                    cout << "connect : " << socket << endl;
                    if (socket != INVALID_SOCKET) {
                        string s = "Welcome [ ";
                        memset(buff1, 0, 20);
                        int l = recv(socket, buff1, 20, 0);
                        if (l > 0) {
                            online_users.insert(pair<SOCKET, string>(socket, buff1));
                            FD_SET(socket, &conn_set);
                            s.append(buff1);
                            s.append(" ] to join the chat room");
                            cout << Coder::Decode(s) << endl;
                            broadcast(socket, s.data());
                        } else {
                            closesocket(socket);
                        }
                    }
                } else if(FD_ISSET(temp_set.fd_array[i], &temp_set)){
                    send_msg(temp_set.fd_array[i]);
                }
            }
        }
        if (quit) {
            break;
        }
    }
}

void ServerCore::send_msg(SOCKET socket) {
    string fp;
    char buff[4096]{0};
    const char *name = online_users.find(socket)->second.data();
    fp.append(name).append("\r\n\r\n");
    int l = recv(socket, buff, 4096, 0);
    if(l > 0) {
        fp.append(buff);
        cout << socket << " say : " << Coder::Decode(buff) << endl;
        broadcast(socket, fp.data());
    } else {
        string st;
        st.append("User [ ").append(name).append(" ] exits the chat room");
        cout << Coder::Decode(st) << endl;
        broadcast(socket, st.data());
        online_users.erase(socket);
        FD_CLR(socket, &conn_set);
        closesocket(socket);
    }
}

void ServerCore::broadcast(SOCKET socket, const char *msg) {
    for (map<SOCKET, string>::iterator it = online_users.begin(); it != online_users.end(); it++) {
        if (it->first != socket) {
            send(it->first, msg, strlen(msg), 0);
        }
    }
}
