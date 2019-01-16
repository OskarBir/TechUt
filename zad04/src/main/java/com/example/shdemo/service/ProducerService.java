package com.example.shdemo.service;

import com.example.shdemo.domain.Producer;

import java.util.List;

public interface ProducerService {

    void addSockToProducer(Long producerId, Long sockId);
    void deleteSockFromProducer(Long producerId, Long sockId);
    Long addProducer(Producer producer);
    List getAllProducers();
    Producer getProducerById(Long id);
    Producer getProducerByName(String name);
    void updateProducer(Producer producer);
    void deleteProducer(Producer producer);
    List getAllSocksFromProducer(Producer producer);

}
