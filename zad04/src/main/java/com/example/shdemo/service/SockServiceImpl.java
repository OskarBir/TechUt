package com.example.shdemo.service;

import com.example.shdemo.domain.Sock;
import com.example.shdemo.domain.Wearer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

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

    @Override
    public void removeSocksProducedBefore(Date date) {
        List<Sock> socks = getAllSocks();
        for(Sock sock : socks){
            if(sock.getDateOfProduction().after(date)){
                deleteSock(sock);
            }
        }
    }

    @Override
    public void giveWearer(Sock sock, Wearer wearer) {
        sock = (Sock)sessionFactory.getCurrentSession().get(Sock.class,sock.getId());
        wearer = (Wearer)sessionFactory.getCurrentSession().get(Wearer.class,wearer.getId());

        if(sock!=null && wearer!=null){
            List<Wearer> socksWearers = sock.getWearerList();
            if(!socksWearers.contains(wearer)){
                socksWearers.add(wearer);
            }
            sock.setWearerList(socksWearers);


            updateSock(sock);
            sessionFactory.getCurrentSession().update(wearer);
        }

    }

    @Override
    public void removeWearer(Sock sock, Wearer wearer) {
        sock = (Sock)sessionFactory.getCurrentSession().get(Sock.class,sock.getId());
        wearer = (Wearer)sessionFactory.getCurrentSession().get(Wearer.class,wearer.getId());
        if(sock!=null && wearer!=null) {
            List<Wearer> socksWearers = sock.getWearerList();
            socksWearers.remove(wearer);
            sock.setWearerList(socksWearers);
        }
        updateSock(sock);
        sessionFactory.getCurrentSession().update(wearer);
    }
}
