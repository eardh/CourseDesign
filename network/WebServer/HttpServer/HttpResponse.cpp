//
// Created by eardh on 2021/12/27.
//

#include <cstring>
#include <string>
#include "HttpResponse.h"
map<string, string> HttpResponse::MAP = initDate();

void HttpResponse::encode(HttpResponse &response, char *msg) {
    std::string  s = response.r_line;
    s += "\r\nServer: ";
    s += response.server;
    s += "\r\nConnection: keep-alive";
    s += "\r\nContent-Length: ";
    s += std::to_string(response.content_length);
    s += "\r\nContent-Type: ";
    s += response.content_type;
    s += "\r\n\r\n";
    strcpy(msg, s.data());
}

HttpResponse HttpResponse::ok(const char *str) {
    HttpResponse response{};
    strcpy(response.r_line, "HTTP/1.1 200 OK");
    char *p= strrchr(str, '.');
    p++;
    auto pos = MAP.find(p);
    if (pos != MAP.end()) {
        strcpy(response.content_type, pos->second.data());
    } else {
        strcpy(response.content_type, "application/octet-stream; charset=utf-8");
    }
    strcpy(response.server, "eardh");
    return response;
}

HttpResponse HttpResponse::error() {
    HttpResponse response;
    strcpy(response.r_line, "HTTP/1.1 500 ERROR");
    response.content_length = 0;
    strcpy(response.server, "eardh");
    strcpy(response.content_type, "text/html; charset=utf-8");
    return response;
}

void HttpResponse::setContentLength(int contentLength) {
    content_length = contentLength;
}

map<string, string> HttpResponse::initDate() {
    map<string, string> map;
    map.insert(pair<string, string>("png", "image/png; charset=utf-8"));
    map.insert(pair<string, string>("gif", "image/gif; charset=utf-8"));
    map.insert(pair<string, string>("jpg", "image/jpeg; charset=utf-8"));
    map.insert(pair<string, string>("txt", "text/plain; charset=utf-8"));
    map.insert(pair<string, string>("docx", "application/msword; charset=utf-8"));
    map.insert(pair<string, string>("xml", "text/xml; charset=utf-8"));
    map.insert(pair<string, string>("html", "text/html; charset=utf-8"));
    map.insert(pair<string, string>("js", "application/javascript; charset=utf-8"));
    map.insert(pair<string, string>("css", "text/css; charset=utf-8"));
    map.insert(pair<string, string>("default", "application/octet-stream; charset=utf-8"));
    return map;
}
