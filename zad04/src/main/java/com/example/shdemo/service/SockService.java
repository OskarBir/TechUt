package com.example.shdemo.service;

import com.example.shdemo.domain.Sock;
import com.example.shdemo.domain.Wearer;

import java.util.Date;
import java.util.List;

public interface SockService {

    void addSock(Sock sock);
    List getAllSocks();
    Sock getSockByName(String name);
    void updateSock(Sock sock);
    void deleteSock(Sock sock);
    void removeSocksProducedBefore(Date date);
    void giveWearer(Sock sock, Wearer wearer);
    void removeWearer(Sock sock, Wearer wearer);

}
