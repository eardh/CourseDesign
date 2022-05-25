//
// Created by eardh on 2021/12/27.
//

#ifndef HTTPSERVER_HTTPRESPONSE_H
#define HTTPSERVER_HTTPRESPONSE_H

#include <map>

using namespace std;

class HttpResponse {
public:
    static map<string, string> MAP;
    static HttpResponse ok(const char *str);
    static HttpResponse error();
    static void encode(HttpResponse &response, char *msg);
    void setContentLength(int contentLength);

private:
    static map<string, string> initDate();

private:
    char r_line[36];
    char server[36];
    int content_length;
    char content_type[36];
};

#endif //HTTPSERVER_HTTPRESPONSE_H
