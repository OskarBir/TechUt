package com.example.shdemo.domain;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "wearer.getAll",query = "Select w from Wearer w"),
        @NamedQuery(name = "wearer.getByName",query = "Select w from Wearer w where :name=w.name")
})
public class Wearer {

    private Long id;
    private String name;

    private List<Sock> socks;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wearer wearer = (Wearer) o;
        return id.equals(wearer.id) &&
                name.equals(wearer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
