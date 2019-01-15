package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import com.example.shdemo.domain.Sock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SockServiceTest {

    @Autowired
    SockService sockService;

    private final String SOCK1_NAME = "elastico";
    private final Boolean SOCK1_COTTON = true;
    private final Double SOCK1_PRICE = 51.2;

    private final String SOCK2_NAME = "faster";
    private final Boolean SOCK2_COTTON = false;
    private final Double SOCK2_PRICE = 12.9;

    @Test
    public void addSockTest(){

        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock : sockList){
            if(sock.getName().equals(SOCK1_NAME)){
                sockService.deleteSock(sock);
            }
        }

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK1_NAME);
        sockToAdd.setCotton(SOCK1_COTTON);
        sockToAdd.setPrice(SOCK1_PRICE);

        sockService.addSock(sockToAdd);

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);

        assertEquals(SOCK1_NAME, addedSock.getName());
        assertEquals(SOCK1_COTTON, addedSock.getCotton());
        assertEquals(SOCK1_PRICE, addedSock.getPrice());
    }

    @Test
    public void updateSockTest(){

        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock : sockList){
            if(sock.getName().equals(SOCK2_NAME)){
                sockService.deleteSock(sock);
            }
        }

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK2_NAME);
        sockToAdd.setCotton(SOCK2_COTTON);
        sockToAdd.setPrice(SOCK2_PRICE);

        sockService.addSock(sockToAdd);

        sockToAdd.setCotton(true);
        sockService.updateSock(sockToAdd);

        Sock updatedSock = sockService.getSockByName(SOCK2_NAME);

        assertEquals(SOCK2_NAME, updatedSock.getName());
        assertEquals(true, updatedSock.getCotton());
        assertEquals(SOCK2_PRICE, updatedSock.getPrice());
    }

    @Test
    public void deleteSockTest(){

        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock : sockList){
            if(sock.getName().equals(SOCK2_NAME) || sock.getName().equals(SOCK1_NAME)){
                sockService.deleteSock(sock);
            }
        }

        Sock sockToDelete = new Sock();
        sockToDelete.setName(SOCK1_NAME);
        sockToDelete.setCotton(SOCK1_COTTON);
        sockToDelete.setPrice(SOCK1_PRICE);

        sockService.addSock(sockToDelete);

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK2_NAME);
        sockToAdd.setCotton(SOCK2_COTTON);
        sockToAdd.setPrice(SOCK2_PRICE);

        sockService.addSock(sockToAdd);

        sockService.deleteSock(sockToDelete);

        List<Sock> socks = sockService.getAllSocks();
        Sock remainedSock = socks.get(0);

        assertEquals(socks.size(),1);
        assertEquals(SOCK2_NAME, remainedSock.getName());
        assertEquals(SOCK2_COTTON, remainedSock.getCotton());
        assertEquals(SOCK2_PRICE, remainedSock.getPrice());

    }




}
