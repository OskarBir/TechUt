package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.shdemo.domain.Producer;
import com.example.shdemo.domain.Sock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ProducerServiceTest {

    @Autowired
    SockService sockService;
    @Autowired
    ProducerService producerService;

    private final String SOCK1_NAME = "elastico";
    private final Boolean SOCK1_COTTON = true;
    private final Double SOCK1_PRICE = 51.2;

    private final String SOCK2_NAME = "faster";
    private final Boolean SOCK2_COTTON = false;
    private final Double SOCK2_PRICE = 12.9;

    private final String PRODUCER_NAME = "Nike";

    @Test
    public void addProducerTest(){
        List<Producer> producers = producerService.getAllProducers();

        for(Producer producer : producers){
            if(producer.getName().equals(PRODUCER_NAME))
                producerService.deleteProducer(producer);
        }

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);

        producerService.addProducer(producerToAdd);

        Producer addedProducer = producerService.getProducerByName(PRODUCER_NAME);

        assertEquals(PRODUCER_NAME, addedProducer.getName());
    }

    @Test
    public void updateProducerTest(){
        List<Producer> producers = producerService.getAllProducers();

        for(Producer producer : producers){
            if(producer.getName().equals(PRODUCER_NAME))
                producerService.deleteProducer(producer);
        }

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);

        producerService.addProducer(producerToAdd);

        Producer addedProducer = producerService.getProducerByName(PRODUCER_NAME);

        assertEquals(PRODUCER_NAME, addedProducer.getName());
    }

    @Test
    public void addSockToProducerTest(){
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


        List<Producer> producers = producerService.getAllProducers();

        for(Producer producer : producers){
            if(producer.getName().equals(PRODUCER_NAME))
                producerService.deleteProducer(producer);
        }

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);
        producerToAdd.getSockList().add(sockToAdd);

        sockService.addSock(sockToAdd);
        producerService.addProducer(producerToAdd);
        Producer addedProducer = producerService.getProducerByName(PRODUCER_NAME);
        Sock producersSock = addedProducer.getSockList().get(0);

        assertEquals(PRODUCER_NAME, addedProducer.getName());
        assertEquals(1, addedProducer.getSockList().size());
        assertEquals(SOCK1_NAME, producersSock.getName());
        assertEquals(SOCK1_PRICE, producersSock.getPrice());
        assertEquals(SOCK1_COTTON, producersSock.getCotton());

    }

    @Test
    public void removeSockFromProducerrTest(){
        List<Sock> sockList = sockService.getAllSocks();

        for (Sock sock : sockList){
            if(sock.getName().equals(SOCK1_NAME) || sock.getName().equals(SOCK2_NAME)){
                sockService.deleteSock(sock);
            }
        }

        Sock sockToRemove = new Sock();
        sockToRemove.setName(SOCK1_NAME);
        sockToRemove.setCotton(SOCK1_COTTON);
        sockToRemove.setPrice(SOCK1_PRICE);

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK2_NAME);
        sockToAdd.setCotton(SOCK2_COTTON);
        sockToAdd.setPrice(SOCK2_PRICE);

        List<Producer> producers = producerService.getAllProducers();

        for(Producer producer : producers){
            if(producer.getName().equals(PRODUCER_NAME))
                producerService.deleteProducer(producer);
        }

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);

        sockService.addSock(sockToRemove);
        sockService.addSock(sockToAdd);
        Long producerId = producerService.addProducer(producerToAdd);

        Sock addedSock = sockService.getSockByName(SOCK2_NAME);
        Sock toRemoveSock = sockService.getSockByName(SOCK1_NAME);

        producerService.addSockToProducer(producerId, addedSock.getId());
        producerService.addSockToProducer(producerId, toRemoveSock.getId());
        producerService.deleteSockFromProducer(producerService.getProducerByName(PRODUCER_NAME).getId(), sockService.getSockByName(SOCK1_NAME).getId());


        Producer addedProducer = producerService.getProducerByName(PRODUCER_NAME);
        Sock prodecersSock = addedProducer.getSockList().get(0);



        assertEquals(PRODUCER_NAME, addedProducer.getName());
        assertEquals(1, addedProducer.getSockList().size());
        assertEquals(SOCK2_NAME, prodecersSock.getName());
        assertEquals(SOCK2_PRICE, prodecersSock.getPrice());
        assertEquals(SOCK2_COTTON, prodecersSock.getCotton());

    }

    @Test
    public void deleteProducerTest(){
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


        List<Producer> producers = producerService.getAllProducers();

        for(Producer producer : producers){
            if(producer.getName().equals(PRODUCER_NAME))
                producerService.deleteProducer(producer);
        }

        Producer producerToAdd = new Producer();
        producerToAdd.setName(PRODUCER_NAME);
        producerToAdd.getSockList().add(sockToAdd);

        sockService.addSock(sockToAdd);
        producerService.addProducer(producerToAdd);

        producerService.deleteProducer(producerToAdd);

        assertEquals(producerService.getAllProducers().size(),0);
        assertEquals(sockService.getAllSocks().size(),0);
    }



}
