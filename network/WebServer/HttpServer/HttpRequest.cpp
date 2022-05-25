//
// Created by eardh on 2021/12/27.
//

#include <cstring>
#include "HttpRequest.h"

HttpRequest HttpRequest::resolve(char *source) {
    char *p = source;
    HttpRequest httpRequest{};
    int i = 0;
    while(*p != ' ') {
        i++;
        p++;
    }
    strncpy(httpRequest.method,  source, i);
    i = 0;
    source = ++p;
    while(*p != ' ') {
        i++;
        p++;
    }
    strncpy(httpRequest.url,  source, i);
    source = ++p;
    i = 0;
    while (*p != '\r') {
        p++;
        i++;
    }
    if(i > 0) {
        strncpy(httpRequest.protocol,  source, i);
    }
    if(strcmp(httpRequest.method, "POST") == 0) {
        char *q = strstr(source, "Content-Length");
        if(q != nullptr) {
            while(*q != ' ') {
                q++;
            }
            source = ++q;
            int m = 0;
            while (*q != '\n') {
                m = m * 10 + *q - '0';
                q++;
            }
            httpRequest.content_length = m;
            q = strstr(source, "\r\n\r\n");
            q += 4;
            strncpy(httpRequest.content, q, httpRequest.content_length);
        }
    }
    return httpRequest;
}

const char *HttpRequest::getUrl() const {
    return url;
}
