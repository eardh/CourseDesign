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
    static char *Decode(char *str, int len);
    static void Encode(char *str);

private:
    static unsigned char ToHex(unsigned char x);
    static unsigned char FromHex(unsigned char x);
    static void UrlEncode(char *str);
    static void UrlDecode(char *str, int len);
};


#endif //HTTPSERVER_CODER_H
