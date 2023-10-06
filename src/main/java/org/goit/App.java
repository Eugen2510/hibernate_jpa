package org.goit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.goit.connection_properties.PathConstants;
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
        TypedQuery<Building> query = manager.createQuery("SELECT b FROM Building b", Building.class);
        List<Building> buildings = query.getResultList();
        for (Building building : buildings) {
            System.out.println(building);
            List<Flat> flats = building.getFlats();
            for (Flat flat : flats) {
                System.out.println(flat);
                List<Person> people = flat.getPersons();
                for (Person person : people) {
                    System.out.println(person);
                }
            }
            System.out.println();
        }

        TypedQuery<Person> query1 = manager.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> people = query1.getResultList();
        for (Person person : people) {
            System.out.println(person);
            List<Ownership> ownerships = person.getOwnerships();
            for (Ownership ownership : ownerships) {
                System.out.println(ownership);
            }
            System.out.println();
        }

        factory.close();
        manager.close();
    }
}
