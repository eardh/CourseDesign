//
// Created by eardh on 2021/12/26.
//

#include "Coder.h"

void Coder::UrlEncode(char *str) {
    string result;
    for (size_t i = 0; i < strlen(str); i++) {
        if (isalnum((unsigned char) str[i]) ||
            (str[i] == '-') ||
            (str[i] == '_') ||
            (str[i] == '.') ||
            (str[i] == '~')) {
            result += str[i];
        } else if (str[i] == ' ') {
            result += '+';
        } else {
            result += '%';
            result += ToHex((unsigned char) str[i] >> 4);
            result += ToHex((unsigned char) str[i] % 16);
        }
    }
    strcpy(str, result.data());
}

void Coder::UrlDecode(char *str, int len) {
    string result;
    for (size_t i = 0; i < len; i++) {
        if (str[i] == '+') {
            result += ' ';
        } else if (str[i] == '%') {
            assert(i + 2 < len);
            unsigned char high = FromHex((unsigned char) str[++i]);
            unsigned char low = FromHex((unsigned char) str[++i]);
            result += high * 16 + low;
        } else {
            result += str[i];
        }
    }
    strcpy(str, result.data());
}

unsigned char Coder::ToHex(unsigned char x) {
    return x > 9 ? x + 55 : x + 48;
}

unsigned char Coder::FromHex(unsigned char x) {
    unsigned char y;
    if (x >= 'A' && x <= 'Z') {
        y = x - 'A' + 10;
    } else if (x >= 'a' && x <= 'z') {
        y = x - 'a' + 10;
    } else if (x >= '0' && x <= '9') {
        y = x - '0';
    } else {
        assert(0);
    }
    return y;
}

char *Coder::Decode(char *bytes, int length) {
    UrlDecode(bytes, length);
    int len = MultiByteToWideChar(CP_UTF8, 0, bytes, -1, nullptr, 0);
    auto *wstr = new wchar_t[len + 1];
    memset(wstr, 0, len + 1);
    MultiByteToWideChar(CP_UTF8, 0, bytes, -1, wstr, len);
    len = WideCharToMultiByte(CP_ACP, 0, wstr, -1, nullptr, 0, nullptr, nullptr);
    char *str = new char[len + 1];
    memset(str, 0, len + 1);
    WideCharToMultiByte(CP_ACP, 0, wstr, -1, str, len, nullptr, nullptr);
    delete[] wstr;
    return str;
}

void Coder::Encode(char *str) {
//    int len = MultiByteToWideChar(CP_ACP, 0, zh.data(), -1, nullptr, 0);
//    auto *wstr = new wchar_t[len+1];
//    memset(wstr, 0, len+1);
//    MultiByteToWideChar(CP_ACP, 0, zh.data(), -1, wstr, len);
//    len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, nullptr, 0, nullptr, nullptr);
//    char *str = new char[len+1];
//    memset(str, 0, len+1);
//    WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, nullptr, nullptr);
//    delete[] wstr;
    UrlEncode(str);
}
