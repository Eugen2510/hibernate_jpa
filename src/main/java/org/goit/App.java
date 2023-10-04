package org.goit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.goit.connection_properties.PathConstants;
import org.goit.entities.Building;
import org.goit.entities.Flat;
import org.goit.entities.Owner;
import org.goit.flyway.MigrationExecutor;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MigrationExecutor executor = new MigrationExecutor(PathConstants.DB_CONNECTION_PROPERTIES_FILE_PATH);
        executor.executeMigrations();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("residential_complex");
        EntityManager manager = factory.createEntityManager();
        TypedQuery<Building> query = manager.createQuery("SELECT b FROM Building b", Building.class);
        List<Building> buildings = query.getResultList();
        for (Building building : buildings) {
            System.out.println(building);
            List<Flat> flats = building.getFlats();
            for (Flat flat : flats) {
                System.out.println(flat);
            }
            System.out.println();
        }

//        TypedQuery<Person> query1 = manager.createQuery("SELECT r FROM Person r", Person.class);
//        List<Person> people = query1.getResultList();
//        for (Person person : people) {
//            System.out.println(person);
//        }
//
        System.out.println('\n');
        TypedQuery<Owner> query2 = manager.createQuery("SELECT o FROM Owner o", Owner.class);
        List<Owner> owners = query2.getResultList();
        for (Owner owner : owners) {
            System.out.println(owner);
            List<Flat> flats = owner.getFlats();
            for (Flat flat : flats) {
                System.out.println(flat);
            }
        }
//
//        TypedQuery<Flat> query3 = manager.createQuery("SELECT f FROM Flat f", Flat.class);
//        List<Flat> flats = query3.getResultList();
//        for (Flat flat : flats) {
//            System.out.println(flat);
//        }


        factory.close();
        manager.close();
    }
}
