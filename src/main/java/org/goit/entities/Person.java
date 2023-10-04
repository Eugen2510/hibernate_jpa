package org.goit.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    @Column(name = "parking_right")
    private int parkingRight;
    @Column(name = "is_owner")
    private int isOwner;
    @Column(name = "is_resident")
    private int isResident;
}
