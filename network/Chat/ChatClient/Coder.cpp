//
// Created by eardh on 2021/12/26.
//

#include <locale>
#include "Coder.h"

string Coder::Decode(const string& utf8Str) {
    string outGBK;
    int n = MultiByteToWideChar(CP_UTF8, 0, utf8Str.c_str(), -1, nullptr, 0);
    auto *str1 = new WCHAR[n];
    MultiByteToWideChar(CP_UTF8, 0, utf8Str.c_str(), -1, str1, n);
    n = WideCharToMultiByte(CP_ACP, 0, str1, -1, nullptr, 0, nullptr, nullptr);
    char *str2 = new char[n];
    WideCharToMultiByte(CP_ACP, 0, str1, -1, str2, n, nullptr, nullptr);
    outGBK = str2;
    delete[] str1;
    delete[] str2;
    return outGBK;
}

string Coder::Encode(const string& gbkStr) {
    string outUtf8;
    int n = MultiByteToWideChar(CP_ACP, 0, gbkStr.c_str(), -1, nullptr, 0);
    auto *str1 = new WCHAR[n];
    MultiByteToWideChar(CP_ACP, 0, gbkStr.c_str(), -1, str1, n);
    n = WideCharToMultiByte(CP_UTF8, 0, str1, -1, nullptr, 0, nullptr, nullptr);
    char *str2 = new char[n];
    WideCharToMultiByte(CP_UTF8, 0, str1, -1, str2, n, nullptr, nullptr);
    outUtf8 = str2;
    delete[]str1;
    delete[]str2;
    return outUtf8;
}
