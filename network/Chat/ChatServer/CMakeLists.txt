cmake_minimum_required(VERSION 3.20)
project(ChatServer)

set(CMAKE_CXX_STANDARD 14)
link_libraries(ws2_32)

add_executable(ChatServer main.cpp ServerCore.cpp ServerCore.h Coder.cpp Coder.h)
