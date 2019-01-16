package com.example.shdemo.service;

import com.example.shdemo.domain.Sock;
import com.example.shdemo.domain.Wearer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class WearerServiceImpl implements WearerService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long addWearer(Wearer wearer) {
        wearer.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(wearer);
    }

    @Override
    public List getAllWearers() {
        return sessionFactory.getCurrentSession().getNamedQuery("wearer.getAll").list();
    }

    @Override
    public Wearer getWearerByName(String name) {
        return (Wearer) sessionFactory.getCurrentSession().getNamedQuery("wearer.getByName").setString("name",name).uniqueResult();
    }


    @Override
    public void updateWearer(Wearer wearer) {
        sessionFactory.getCurrentSession().update(wearer);
    }

    @Override
    public void deleteWearer(Wearer wearer) {
        wearer = (Wearer) sessionFactory.getCurrentSession().get(Wearer.class, wearer.getId());
        if (wearer != null) {
            List<Sock> socks = sessionFactory.getCurrentSession().getNamedQuery("sock.getAll").list();
            for (Sock sock : socks) {
                if (sock.getWearerList().contains(wearer)) {
                    sock.getWearerList().remove(wearer);
                    sessionFactory.getCurrentSession().update(sock);
                }
            }
            sessionFactory.getCurrentSession().delete(wearer);
        }
    }
}
