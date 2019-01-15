package com.example.shdemo.service;

import com.example.shdemo.domain.Producer;
import com.example.shdemo.domain.Sock;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addSockToProducer(Long producerId, Long sockId) {
        Producer producer = getProducerById(producerId);
        Sock sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, sockId);
        producer.getSockList().add(sock);
    }

    @Override
    public void deleteSockFromProducer(Long producerId, Long sockId) {
        Producer producer = getProducerById(producerId);
        Sock sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, sockId);
        if (producer.getSockList().contains(sock)){
            producer.getSockList().remove(sock);
        }
    }

    @Override
    public Long addProducer(Producer producer) {
        producer.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(producer);
    }

    @Override
    public List getAllProducers() {
        return sessionFactory.getCurrentSession().getNamedQuery("producer.getAll").list();
    }

    @Override
    public Producer getProducerById(Long id) {
        return (Producer) sessionFactory.getCurrentSession().getNamedQuery("producer.getById").setLong("id", id).uniqueResult();
    }

    @Override
    public Producer getProducerByName(String name) {
        return (Producer) sessionFactory.getCurrentSession().getNamedQuery("producer.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public void updateProducer(Producer producer) {
        sessionFactory.getCurrentSession().update(producer);
    }

    @Override
    public void deleteProducer(Producer producer) {
        producer = (Producer) sessionFactory.getCurrentSession().get(Producer.class, producer.getId());
        for (Sock sock : producer.getSockList()) {
            sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, sock.getId());
            sessionFactory.getCurrentSession().delete(sock);
        }

        sessionFactory.getCurrentSession().delete(producer);
    }

    @Override
    public List getAllSocksFromProducer(Producer producer) {
        List<Sock> result = new ArrayList<Sock>();

        result.addAll(producer.getSockList());

        return result;

    }
}
