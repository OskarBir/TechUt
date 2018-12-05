package com.example.shdemo.domain;

import javax.persistence.*;


@Entity
public class Sock {
    private Long id;
    private String brand;
    private int size;
    private boolean cotton = false;
    private float price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean getCotton() {
        return cotton;
    }

    public void setCotton(Boolean cotton) {
        this.cotton = cotton;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
