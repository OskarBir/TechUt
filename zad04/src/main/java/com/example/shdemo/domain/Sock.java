package com.example.shdemo.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "sock.getAll",query = "Select s from Sock s"),
                @NamedQuery(name = "sock.getByName", query = "Select s from Sock s where s.name = :name")
        }
)
public class Sock {

    private Long id;
    private String name;
    private Boolean cotton;
    private Double price;
    private Date dateOfProduction;
    private List<Wearer> wearerList = new ArrayList<Wearer>();
    private Label label;

    @Override
    public String toString() {
        return "\nSock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfProduction='" + new SimpleDateFormat("yyyy-MM-dd").format(dateOfProduction) + '\'' +
                ", cotton=" + cotton +
                ", price=" + price +
                "}";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCotton() {
        return cotton;
    }

    public void setCotton(Boolean hasCotton) {
        cotton = hasCotton;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Wearer> getWearerList() {
        return wearerList;
    }

    public void setWearerList(List<Wearer> wearerList) {
        this.wearerList = wearerList;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sock sock = (Sock) o;
        return Objects.equals(id, sock.id) &&
                Objects.equals(name, sock.name) &&
                Objects.equals(cotton, sock.cotton) &&
                Objects.equals(price, sock.price) &&
                Objects.equals(dateOfProduction, sock.dateOfProduction) &&
                Objects.equals(wearerList, sock.wearerList) &&
                Objects.equals(label, sock.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cotton, price, dateOfProduction, wearerList, label);
    }
}
