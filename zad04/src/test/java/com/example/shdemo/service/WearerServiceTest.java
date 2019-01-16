package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import com.example.shdemo.domain.Sock;
import com.example.shdemo.domain.Wearer;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class WearerServiceTest {

    @Autowired
    WearerService wearerService;
    @Autowired
    SockService sockService;

    private final String WEARER_NAME = "Adam Kowalski";

    private final String SOCK1_NAME = "elastico";
    private final Boolean SOCK1_COTTON = true;
    private final Double SOCK1_PRICE = 51.2;
    private Date SOCK1_DATE;
    {
        try {
            SOCK1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void setUp(){
        List<Wearer> allWearers = wearerService.getAllWearers();

        for (Wearer wearer: allWearers){
            if(wearer.getName().equals(WEARER_NAME)){
                wearerService.deleteWearer(wearer);
            }
        }
        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock: sockList){
            if(sock.getName().equals(SOCK1_NAME)){
                sockService.deleteSock(sock);
            }
        }

    }

    @Test
    public void addWearerTest(){
        Wearer wearerToAdd = new Wearer();
        wearerToAdd.setName(WEARER_NAME);

        wearerService.addWearer(wearerToAdd);

        List<Wearer> allWearers = wearerService.getAllWearers();
        assertEquals(1,allWearers.size());


        Wearer addedWearer = allWearers.get(0);

        assertNotNull(addedWearer);

        assertEquals(WEARER_NAME,addedWearer.getName());
    }

    @Test
    public void updateWearerTest(){
        Wearer wearerToAdd = new Wearer();
        wearerToAdd.setName(WEARER_NAME);

        wearerService.addWearer(wearerToAdd);

        Wearer addedWearer = wearerService.getWearerByName(WEARER_NAME);
        addedWearer.setName("Ewa Kowalska");
        wearerService.updateWearer(addedWearer);

        List<Wearer> allWearers = wearerService.getAllWearers();
        assertEquals(1,allWearers.size());

        Wearer updatedWearer = allWearers.get(0);

        assertNotNull(updatedWearer);

        assertEquals("Ewa Kowalska",updatedWearer.getName());
    }

    @Test
    public void deleteWearerTest(){
        Wearer wearerToAdd = new Wearer();
        wearerToAdd.setName(WEARER_NAME);

        wearerService.addWearer(wearerToAdd);
        Wearer wearerToDelete = wearerService.getWearerByName(WEARER_NAME);
        wearerService.deleteWearer(wearerToDelete);

        List<Wearer> allWearers = wearerService.getAllWearers();
        assertEquals(0,allWearers.size());
    }

    @Test
    public void deleteWearerAssignedToSockTest(){
        Wearer wearerToAdd = new Wearer();
        wearerToAdd.setName(WEARER_NAME);

        wearerService.addWearer(wearerToAdd);
        Wearer addedWearer = wearerService.getWearerByName(WEARER_NAME);

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK1_NAME);
        sockToAdd.setCotton(SOCK1_COTTON);
        sockToAdd.setPrice(SOCK1_PRICE);
        sockToAdd.setDateOfProduction(SOCK1_DATE);
        sockService.addSock(sockToAdd);

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);
        sockService.giveWearer(addedSock,addedWearer);

        wearerService.deleteWearer(addedWearer);

        List<Wearer> wearers = wearerService.getAllWearers();
        assertEquals(0,wearers.size());
        Sock sockAfterDeletionOfWearer = sockService.getSockByName(SOCK1_NAME);
        assertNotNull(sockAfterDeletionOfWearer);
        assertEquals(0,sockAfterDeletionOfWearer.getWearerList().size());


    }




}