package com.example.jdbcdemo.domain;

public class Sock {
	
	private long id;
	
	private String name;
	private String brand;
	private int size;
	
	public Sock() {
	}
	
	public Sock(String name,String brand, int size) {
		super();
		this.name = name;
		this.brand = brand;
		this.size = size;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
