package com.example.shdemo.service;

import com.example.shdemo.domain.Label;
import com.example.shdemo.domain.Sock;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class LabelServiceImpl implements LabelService{

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List getAllLabels() {
        return sessionFactory.getCurrentSession().getNamedQuery("label.getAll").list();
    }

    @Override
    public void addLabelToSock(Long labelId, Long sockId) {
        Label label = getLabelById(labelId);
        Sock sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, sockId);
        sock.setLabel(label);
    }

    @Override
    public Long addLabel(Label label) {
        label.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(label);
    }

    @Override
    public Label getLabelById(Long labelId) {
        return (Label) sessionFactory.getCurrentSession().getNamedQuery("label.getById").setLong("id",labelId).uniqueResult();
    }

    @Override
    public Label getLabelByLabel(String label) {
        return (Label) sessionFactory.getCurrentSession().getNamedQuery("label.getByLabel").setString("label",label).uniqueResult();
    }

    @Override
    public void updateLabel(Label label) {
        sessionFactory.getCurrentSession().update(label);
    }

    @Override
    public void deleteLabel(Label label) {
        label = (Label) sessionFactory.getCurrentSession().get(Label.class, label.getId());
        if(label.getSock().getId()!=null){
            Sock sock = (Sock) sessionFactory.getCurrentSession().get(Sock.class, label.getSock().getId());
            sock.setLabel(null);
            sessionFactory.getCurrentSession().update(sock);
        }
        sessionFactory.getCurrentSession().delete(label);
    }
}