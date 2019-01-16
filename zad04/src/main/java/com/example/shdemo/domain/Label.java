package com.example.shdemo.domain;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "label.getAll", query = "Select l from Label l"),
        @NamedQuery(name = "label.getById", query = "Select l from Label l where :id=l.id"),
        @NamedQuery(name = "label.getByLabel", query = "Select l from Label l where :label=l.label")
})
public class Label {

    private Long id;
    private String label;
    private Sock sock;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel(){return label;}

    public void setLabel(String label){this.label = label;}

    @OneToOne(fetch = FetchType.LAZY)
    public Sock getSock() {
        return sock;
    }

    public void setSock(Sock sock) {
        this.sock = sock;
    }

}
