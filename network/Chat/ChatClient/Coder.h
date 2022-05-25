//
// Created by eardh on 2021/12/26.
//

#ifndef HTTPSERVER_CODER_H
#define HTTPSERVER_CODER_H

#include <iostream>
#include <cassert>
#include <stringapiset.h>

using namespace std;

class Coder {

public:
    static string Decode(const string& utf8Str);
    static string Encode(const string& gbkStr);
};


#endif //HTTPSERVER_CODER_H
