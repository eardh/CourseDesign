#include <iostream>
#include <algorithm>
#include "ClientCore.h"

int main() {
    system("color F0");
    string ip;
    cout << Coder::Decode("请输入IP地址 ：");
    cin >> ip;
    cout << Coder::Decode("请输入聊天昵称 ：");
    string name;
    cin >> name;
    ClientCore boss(ip.data(), 9527, name);
    boss.run();
    return 0;
}