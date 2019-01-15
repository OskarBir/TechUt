package com.example.shdemo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "producer.getAll",query = "Select p from Producer p"),
        @NamedQuery(name = "producer.getById",query = "Select p from Producer p where :id=p.id"),
        @NamedQuery(name = "producer.getByName",query = "Select p from Producer p where :name=p.name")
})
public class Producer {

    private Long id;
    private String name;

    private List<Sock> sockList = new ArrayList<Sock>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Sock> getSockList() {
        return sockList;
    }

    public void setSockList(List<Sock> sockList) {
        this.sockList = sockList;
    }
}
