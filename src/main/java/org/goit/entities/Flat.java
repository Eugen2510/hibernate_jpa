package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    @Column(name = "area")
    private int area;

    @Column(name ="floor")
    private int floor;

    @Column(name = "num_of_rooms")
    private int numOfRooms;

    @OneToMany(mappedBy = "residentialFlat")
    List<Person> persons;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", building=" + building +
                ", apartmentNumber=" + apartmentNumber +
                ", area=" + area +
                ", floor=" + floor +
                ", numOfRooms=" + numOfRooms +
                '}';
    }
}
