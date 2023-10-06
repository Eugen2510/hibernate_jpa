package org.goit;

import jakarta.persistence.*;
import org.goit.connection_properties.PathConstants;
import org.goit.crud.CRUD;
import org.goit.entities.Building;
import org.goit.entities.Flat;
import org.goit.entities.Ownership;
import org.goit.entities.Person;
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

//        EntityTransaction transaction = manager.getTransaction();
//
//        transaction.begin();
//        Flat flat1 = manager.find(Flat.class, 2);
//
//        if (flat1 != null) {
//
//            manager.remove(flat1);
//            System.out.println("Flat removed: " + flat1);
//        } else {
//            System.out.println("Flat with ID 2 not found.");
//        }
//
//        transaction.commit();








        TypedQuery<Building> query = manager.createQuery("SELECT b FROM Building b", Building.class);
        List<Building> buildings = query.getResultList();
        for (Building building : buildings) {
            System.out.println(building);
            List<Flat> flats = building.getFlats();
            for (Flat flat : flats) {
                System.out.println(flat);
                List<Person> people = flat.getPersons();
                for (Person person1 : people) {
                    System.out.println(person1);
                }
            }
            System.out.println();
        }

        TypedQuery<Person> query1 = manager.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> people = query1.getResultList();
        for (Person person2 : people) {
            System.out.println(person2);
            List<Ownership> ownerships = person2.getOwnerships();
            for (Ownership ownership : ownerships) {
                System.out.println(ownership);
            }
            System.out.println();
        }

//        transaction.begin();
        CRUD crud = new CRUD(manager);
        Building building = new Building();
        building.setAddress("Stusa 10");
        building.setNumOfFlats(100);
        crud.create(factory, building);
        Building building1 = new Building();
        building.setAddress("Stusa 11");
        building.setNumOfFlats(100);
        crud.create(factory, building1 );
        Building building2 = new Building();
        building.setAddress("Stusa 12");
        building.setNumOfFlats(100);
        crud.create(factory, building2);




//        transaction.commit();




        factory.close();
        manager.close();
    }
}
