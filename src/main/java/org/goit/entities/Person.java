package org.goit.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "parking_right")
    private int parkingRight;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Ownership> ownerships;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "residential_flat", referencedColumnName = "id")
    private Flat residentialFlat;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", parkingRight=" + parkingRight +
                ", residentialFlatId=" + residentialFlat.getId() +
                '}';
    }
}
