#include <iostream>
#include "ServerCore.h"

int main() {
    system("color F0");
    string ip;
    ServerCore boss(ip, 9527);
    boss.run();
    return 0;
}
