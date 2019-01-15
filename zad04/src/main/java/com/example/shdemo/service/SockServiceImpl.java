package com.example.shdemo.service;

import com.example.shdemo.domain.Sock;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class SockServiceImpl implements SockService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addSock(Sock sock) {
        sock.setId(null);
        sessionFactory.getCurrentSession().persist(sock);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sock> getAllSocks() {
        return sessionFactory.getCurrentSession().getNamedQuery("sock.getAll").list();
    }

    @Override
    public Sock getSockByName(String name) {
        return (Sock) sessionFactory.getCurrentSession().getNamedQuery("sock.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public void updateSock(Sock sock) {
        sessionFactory.getCurrentSession().update(sock);
    }

    @Override
    public void deleteSock(Sock sock) {
        sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, sock.getId());
        sessionFactory.getCurrentSession().delete(sock);

    }
}
