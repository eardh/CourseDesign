cmake_minimum_required(VERSION 3.20)
project(HttpServer)

set(CMAKE_CXX_STANDARD 14)
link_libraries(ws2_32)

add_executable(HttpServer main.cpp Boss.cpp Boss.h Worker.cpp Worker.h Coder.cpp Coder.h HttpRequest.cpp HttpRequest.h HttpResponse.cpp HttpResponse.h)
