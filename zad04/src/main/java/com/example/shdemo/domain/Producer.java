package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Producer {
    private Long id;

    private String name = "unknown";

    private List<Sock> socks = new ArrayList<Sock>();

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

    // Be careful here, both with lazy and eager fetch type
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Sock> getSocks() {
        return socks;
    }
    public void setSocks(List<Sock> socks) {
        this.socks = socks;
    }
}
