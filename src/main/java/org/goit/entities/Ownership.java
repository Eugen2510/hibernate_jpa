package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ownership implements MarkEntities{

    @Transient
    private final static String sqlQuery = "SELECT o FROM Ownership o";
    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "flat_id", referencedColumnName = "id")
    private Flat flat;

    @Override
    public String toString() {
        return "Ownership{" +
                "personId=" + person.getId() +
                ", flatId=" + flat.getId() +
                '}';
    }

    @Override
    public String getSqlQuery(){
        return sqlQuery;
    }

    @Override
    public void clone(MarkEntities entity) {

    }

    @Override
    public Ownership findById(EntityManager manager, int id) {
        return null;
    }

    @Override
    public void delete(EntityManager manager, int id) {

    }
}
