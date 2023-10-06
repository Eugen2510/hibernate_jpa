package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num_of_flats")
    private int numOfFlats;

    private String address;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Flat> flats;

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", numOfFlats=" + numOfFlats +
                ", address='" + address + '\'' +
                '}';
    }
}
