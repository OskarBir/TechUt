package ug.obir.jdbcdemo.domain;

public class Sock {

    private long id;

    private String brand;
    private int size;
    private boolean cotton;
    private float price;

    public Sock(long id, String brand, int size, boolean cotton, float price) {
        this(brand, size, cotton, price);
        this.id = id;
    }

    public Sock(String brand, int size, boolean cotton, float price) {
        this.brand = brand;
        this.size = size;
        this.cotton = cotton;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setCotton(boolean cotton) {
        this.cotton = cotton;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id= " + id + ", brand= " + brand + ", size= " + size + ", cotton= " + cotton + ", price= " + price;
    }

}