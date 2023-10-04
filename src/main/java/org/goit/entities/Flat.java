package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

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
    private int area;
    private int floor;
    @Column(name = "num_of_rooms")
    private int numOfRooms;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", buildingId=" + building.getId() +
                ", apartmentNumber=" + apartmentNumber +
                ", area=" + area +
                ", floor=" + floor +
                ", numOfRooms=" + numOfRooms +
                ", ownerId=" + owner.getId() +
                '}';
    }
}
