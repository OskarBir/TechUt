package com.example.shdemo.service;

import com.example.shdemo.domain.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SockServiceTest {

    @Autowired
    SockService sockService;
    @Autowired
    WearerService wearerService;
    @Autowired
    ProducerService producerService;

    private Date BEFORE_DATE;

    private final String WEARER_NAME = "Adam Kowalski";

    private final String SOCK1_NAME = "elastico";
    private final Boolean SOCK1_COTTON = true;
    private final Double SOCK1_PRICE = 51.2;
    private Date SOCK1_DATE;

    private final String SOCK2_NAME = "faster";
    private final Boolean SOCK2_COTTON = false;
    private final Double SOCK2_PRICE = 12.9;
    private Date SOCK2_DATE;
    

    private final String PRODUCER_NAME = "Nike";

    {
        try {
            SOCK1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("11-02-2018");
            SOCK2_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("25-12-2011");
            BEFORE_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("23-04-2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Before
    public void setUp(){
        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock: sockList){
            if(sock.getName().equals(SOCK1_NAME) || sock.getName().equals(SOCK2_NAME)){
                sockService.deleteSock(sock);
            }
        }
        List<Wearer> allWearers = wearerService.getAllWearers();

        for (Wearer wearer: allWearers){
            if(wearer.getName().equals(WEARER_NAME)){
                wearerService.deleteWearer(wearer);
            }
        }
    }

    @Test
    public void addSockTest(){
        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK1_NAME);
        sockToAdd.setCotton(SOCK1_COTTON);
        sockToAdd.setPrice(SOCK1_PRICE);
        sockToAdd.setDateOfProduction(SOCK1_DATE);

        sockService.addSock(sockToAdd);

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);

        assertEquals(SOCK1_NAME,addedSock.getName());
        assertEquals(SOCK1_COTTON,addedSock.getCotton());
        assertEquals(SOCK1_PRICE,addedSock.getPrice());
        assertEquals(SOCK1_DATE,addedSock.getDateOfProduction());
    }

    @Test
    public void updateSockTest(){
        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK2_NAME);
        sockToAdd.setCotton(SOCK2_COTTON);
        sockToAdd.setPrice(SOCK2_PRICE);
        sockToAdd.setDateOfProduction(SOCK2_DATE);

        sockService.addSock(sockToAdd);

        sockToAdd.setCotton(true);
        sockService.updateSock(sockToAdd);

        Sock updatedSock = sockService.getSockByName(SOCK2_NAME);

        assertEquals(SOCK2_NAME,updatedSock.getName());
        assertEquals(true,updatedSock.getCotton());
        assertEquals(SOCK2_PRICE,updatedSock.getPrice());
        assertEquals(SOCK2_DATE,updatedSock.getDateOfProduction());

    }

    @Test
    public void deleteSockTest(){
        Sock sockToDelete = new Sock();
        sockToDelete.setName(SOCK1_NAME);
        sockToDelete.setCotton(SOCK1_COTTON);
        sockToDelete.setPrice(SOCK1_PRICE);
        sockToDelete.setDateOfProduction(SOCK1_DATE);

        sockService.addSock(sockToDelete);

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK2_NAME);
        sockToAdd.setCotton(SOCK2_COTTON);
        sockToAdd.setPrice(SOCK2_PRICE);
        sockToAdd.setDateOfProduction(SOCK2_DATE);

        sockService.addSock(sockToAdd);

        sockService.deleteSock(sockToDelete);

        List<Sock> socks = sockService.getAllSocks();
        Sock remainedSock = socks.get(0);

        assertEquals(socks.size(),1);
        assertEquals(SOCK2_NAME,remainedSock.getName());
        assertEquals(SOCK2_COTTON,remainedSock.getCotton());
        assertEquals(SOCK2_PRICE,remainedSock.getPrice());
        assertEquals(SOCK2_DATE,remainedSock.getDateOfProduction());
    }

    @Test
    public void deleteSockWithProducerAssociatedTest(){
        Sock sockToDelete = new Sock();
        sockToDelete.setName(SOCK1_NAME);
        sockToDelete.setCotton(SOCK1_COTTON);
        sockToDelete.setPrice(SOCK1_PRICE);
        sockToDelete.setDateOfProduction(SOCK1_DATE);

        sockService.addSock(sockToDelete);

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);

        producerService.addProducer(producerToAdd);

        sockToDelete = sockService.getSockByName(SOCK1_NAME);
        producerToAdd = producerService.getProducerByName(PRODUCER_NAME);

        producerService.addSockToProducer(producerToAdd.getId(),sockToDelete.getId());

        sockService.deleteSock(sockToDelete);

    }


    @Test
    public void removeSocksProducedBeforeTest(){
        Sock firstSock = new Sock();
        firstSock.setName(SOCK1_NAME);
        firstSock.setCotton(SOCK1_COTTON);
        firstSock.setPrice(SOCK1_PRICE);
        firstSock.setDateOfProduction(SOCK1_DATE);
        sockService.addSock(firstSock);

        Sock secondSock = new Sock();
        secondSock.setName(SOCK2_NAME);
        secondSock.setCotton(SOCK2_COTTON);
        secondSock.setPrice(SOCK2_PRICE);
        secondSock.setDateOfProduction(SOCK2_DATE);

        sockService.addSock(secondSock);

        sockService.removeSocksProducedBefore(BEFORE_DATE);

        //first sock should be removed

        firstSock = sockService.getSockByName(SOCK1_NAME);
        secondSock = sockService.getSockByName(SOCK2_NAME);
        assertNull(firstSock);
        assertNotNull(secondSock);

        List<Sock> socks = sockService.getAllSocks();
        assertEquals(1,socks.size());
        assertEquals(secondSock,socks.get(0));

    }

    @Test
    public void giveWearerTest(){
        Sock sock = new Sock();
        sock.setName(SOCK1_NAME);
        sock.setCotton(SOCK1_COTTON);
        sock.setPrice(SOCK1_PRICE);
        sock.setDateOfProduction(SOCK1_DATE);

        Wearer wearer = new Wearer();
        wearer.setName(WEARER_NAME);

        sockService.addSock(sock);
        wearerService.addWearer(wearer);

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);
        Wearer addedWearer = wearerService.getWearerByName(WEARER_NAME);

        assertNotNull(addedSock);
        assertNotNull(addedWearer);

        sockService.giveWearer(addedSock,addedWearer);

        addedSock = sockService.getSockByName(SOCK1_NAME);

        assertEquals(1,addedSock.getWearerList().size());

        addedWearer = addedSock.getWearerList().get(0);

        assertEquals(WEARER_NAME,addedWearer.getName());
    }

    @Test
    public void multipleGiveWearerTest(){
        Sock firstSock = new Sock();
        firstSock.setName(SOCK1_NAME);
        firstSock.setCotton(SOCK1_COTTON);
        firstSock.setPrice(SOCK1_PRICE);
        firstSock.setDateOfProduction(SOCK1_DATE);

        sockService.addSock(firstSock);

        Sock secondSock = new Sock();
        secondSock.setName(SOCK2_NAME);
        secondSock.setCotton(SOCK2_COTTON);
        secondSock.setPrice(SOCK2_PRICE);
        secondSock.setDateOfProduction(SOCK2_DATE);

        sockService.addSock(secondSock);

        Wearer wearer = new Wearer();
        wearer.setName(WEARER_NAME);

        wearerService.addWearer(wearer);

        firstSock = sockService.getSockByName(SOCK1_NAME);
        secondSock = sockService.getSockByName(SOCK1_NAME);
        wearer = wearerService.getWearerByName(WEARER_NAME);

        //assigning same wearer to both socks

        sockService.giveWearer(firstSock,wearer);
        sockService.giveWearer(secondSock,wearer);

        firstSock = sockService.getSockByName(SOCK1_NAME);
        secondSock = sockService.getSockByName(SOCK1_NAME);

        assertEquals(1,firstSock.getWearerList().size());
        assertEquals(1,secondSock.getWearerList().size());

        Wearer firstSocksWearer = firstSock.getWearerList().get(0);
        Wearer secondSocksWearer = secondSock.getWearerList().get(0);

        assertEquals(firstSocksWearer,secondSocksWearer);
    }

    @Test
    public void removeWearerTest(){
        Sock firstSock = new Sock();
        firstSock.setName(SOCK1_NAME);
        firstSock.setCotton(SOCK1_COTTON);
        firstSock.setPrice(SOCK1_PRICE);
        firstSock.setDateOfProduction(SOCK1_DATE);

        sockService.addSock(firstSock);

        Sock secondSock = new Sock();
        secondSock.setName(SOCK2_NAME);
        secondSock.setCotton(SOCK2_COTTON);
        secondSock.setPrice(SOCK2_PRICE);
        secondSock.setDateOfProduction(SOCK2_DATE);

        sockService.addSock(secondSock);

        Wearer wearer = new Wearer();
        wearer.setName(WEARER_NAME);

        wearerService.addWearer(wearer);

        firstSock = sockService.getSockByName(SOCK1_NAME);
        secondSock = sockService.getSockByName(SOCK2_NAME);
        wearer = wearerService.getWearerByName(WEARER_NAME);

        //assigning same wearer to both socks

        sockService.giveWearer(firstSock,wearer);
        sockService.giveWearer(secondSock,wearer);
        sockService.removeWearer(firstSock,wearer);

        firstSock = sockService.getSockByName(SOCK1_NAME);
        secondSock = sockService.getSockByName(SOCK2_NAME);

        assertEquals(0,firstSock.getWearerList().size());
        assertEquals(1,secondSock.getWearerList().size());

        Wearer secondSocksWearer = secondSock.getWearerList().get(0);

        assertEquals(secondSocksWearer.getName(),WEARER_NAME);
    }




}