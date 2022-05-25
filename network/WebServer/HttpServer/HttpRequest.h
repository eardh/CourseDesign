//
// Created by eardh on 2021/12/27.
//

#ifndef HTTPSERVER_HTTPREQUEST_H
#define HTTPSERVER_HTTPREQUEST_H


class HttpRequest {
public:
    static HttpRequest resolve(char *source);
    const char *getUrl() const;

private:
    char protocol[16]{0};
    char method[16]{0};
    char url[128]{0};
    int content_length{0};
    char content[1024]{0};
};

#endif //HTTPSERVER_HTTPREQUEST_H
