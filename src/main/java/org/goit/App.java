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
        CRUD crud = new CRUD(factory);
        Building building = new Building();
        building.setAddress("Stusa 1");
        building.setNumOfFlats(131);


        Person person = new Person();
        person.setName("aaa");
        person.setEmail("@@@");
        person.setPhone("444444");
        person.setResidentialFlat((Flat) crud.getEntityById(new Flat(), 1));

//        crud.updateEntity(person,1);
        crud.delete(new Building(), 1);
        List<Person> entities = crud.getAllEntities(new Person());
        for (Person entity : entities) {
            System.out.println(entity);
        }
        crud.getNeededResidents();
        factory.close();
        manager.close();
    }
}
