package com.eardh.chatroom.utils;

public interface MessageState {
    int CONNECT_FAILED = -1;
    int CONNECT_SUCCESS = 1;
    int UPDATE_UI = 2;
    int SERVER_EXCEPTION = 3;
    int UNKNOWN_ERROR = 4;
    int NOT_FIND_CONNECT = 5;
    int SEND_SUCCESS = 6;
}
