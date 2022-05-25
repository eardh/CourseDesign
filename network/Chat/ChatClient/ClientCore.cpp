//
// Created by eardh on 2021/12/25.
//

#include "ClientCore.h"

#include <utility>

ClientCore::ClientCore(const string& ip, int port, string name) {
    IP = ip.empty()  ? DEFAULT_ADDRESS : ip;
    PORT = port;
    quit = false;
    nick_name = std::move(name);
    client_socket = INVALID_SOCKET;
}

ClientCore::~ClientCore() {
    if (client_socket != INVALID_SOCKET) {
        quit = true;
        closesocket(client_socket);
        WSACleanup();
    }
}

int ClientCore::run() {
    WSADATA wsaData;
    int iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (iResult != NO_ERROR) {
        wprintf(L"WSAStartup failed with error: %ld\n", iResult);
        return 1;
    }
    client_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (client_socket == INVALID_SOCKET) {
        wprintf(L"socket failed with error: %ld\n", WSAGetLastError());
        WSACleanup();
        return 1;
    }
    sockaddr_in address{};
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr(IP.c_str());
    address.sin_port = htons(PORT);
    iResult = connect(client_socket, (SOCKADDR *) & address, sizeof (address));
    if (iResult == SOCKET_ERROR) {
        wprintf(L"connect function failed with error: %ld\n", WSAGetLastError());
        iResult = closesocket(client_socket);
        if (iResult == SOCKET_ERROR) {
            wprintf(L"closesocket function failed with error: %ld\n", WSAGetLastError());
        }
        WSACleanup();
        return 1;
    }
    thread t(&ClientCore::receive, this);
    t.detach();
    string name_utf8 = Coder::Encode(nick_name);
    int l = send(client_socket, name_utf8.c_str(), strlen(name_utf8.c_str()), 0);
    if (l < 0) {
        wprintf(L"server error \n");
        WSACleanup();
        return 1;
    }
    while (true) {
        string msg;
        if (quit) {
            break;
        }
        cin >> msg;
        if (quit || msg == "exit") {
            break;
        }
        msg = Coder::Encode(msg);
        send(client_socket, msg.c_str(), strlen(msg.c_str()), 0);
    }
    closesocket(client_socket);
    WSACleanup();
    return 0;
}

void ClientCore::receive() {
    char buff[4096];
    while(true) {
        memset(buff, 0, 4096);
        string content;
        int l = recv(client_socket, buff, 4096, 0);
        if (l > 0) {
            char *p = strstr(buff, "\r\n\r\n");
            if (p == nullptr) {
                cout << Coder::Decode(buff) << endl;
            } else {
                content.append(buff, p - buff)
                       .append(" : ")
                       .append(p + 4);
                cout << Coder::Decode(content) << endl;
            }
        } else {
            cout << Coder::Decode("服务器异常") << endl;
            quit = true;
            break;
        }
    }
}

