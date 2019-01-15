package com.example.shdemo.service;

import com.example.shdemo.domain.Sock;

import java.util.List;

public interface SockService {

    void addSock(Sock sock);
    List getAllSocks();
    Sock getSockByName(String name);
    void updateSock(Sock sock);
    void deleteSock(Sock sock);





}
