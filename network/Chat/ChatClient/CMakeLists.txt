cmake_minimum_required(VERSION 3.20)
project(ChatClient)

set(CMAKE_CXX_STANDARD 14)
link_libraries(ws2_32)

add_executable(ChatClient main.cpp ClientCore.cpp ClientCore.h Coder.h Coder.cpp)
