package org.goit.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.goit.entities.MarkEntities;
import org.goit.entities.Ownership;
import org.goit.entities.Person;

import java.util.List;

public class CRUD {
    private final EntityManagerFactory factory;

    public CRUD(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public <T extends MarkEntities> void createEntity(T... entities) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();

            for (T entity : entities) {
                manager.persist(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public <T extends MarkEntities> List<T> getAllEntities(T entity) {
        List<T> result;
        try (EntityManager manager = factory.createEntityManager()) {
            String query = entity.getSqlQuery();
            result = manager.createQuery(query).getResultList();
        }
        return result;
    }

    public <T extends MarkEntities> MarkEntities getEntityById(T entity, int id){
        EntityManager manager = factory.createEntityManager();
        MarkEntities byId = entity.findById(manager, id);
        return byId;
    }


    public <T extends MarkEntities>  void updateEntity(T entity, int id){
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            MarkEntities byId = entity.findById(manager, id);
            byId.clone(entity);
            transaction.begin();
            manager.merge(byId);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            manager.close();
        }
    }

    public <T extends MarkEntities> void delete(T entity, int id) {
        EntityManager manager = factory.createEntityManager();
        entity.delete(manager, id);
    }


    public void getNeededResidents() {
        CriteriaBuilder builder = factory.createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);

        Root<Ownership> root = criteriaQuery.from(Ownership.class);

//        criteriaQuery.select(residentRoot.get("personId").get("id"))
//                .groupBy(residentRoot.get("personId"))
//                .having(criteriaBuilder.gt(criteriaBuilder.count(residentRoot.get("apartmentId")), 1));


        criteriaQuery.select(
                        root.get("person").get("id"))
                .groupBy(root.get("person"))
                .having(builder.le(builder.count(root.get("flat")), 1));

        List<Integer> ownersIds = factory.createEntityManager().createQuery(criteriaQuery).getResultList();


        System.out.println("ownersIds = " + ownersIds);

        CriteriaQuery<Person> criteriaQuery1 = builder.createQuery(Person.class);
        Root<Person> root1 = criteriaQuery1.from(Person.class);

        Predicate predicate = root1.get("id").in(ownersIds);
        Predicate predicate1 = builder.equal(root1.get("parkingRight"), 1);
        Predicate predicate2  = builder.gt(root1.get("residentialFlat").get("id"), 0);



        criteriaQuery1
                .where(builder.and(predicate, predicate1, predicate2));


        List<Person> resultList = factory.createEntityManager().createQuery(criteriaQuery1).getResultList();
        for (Person person : resultList) {
            System.out.println(person);
        }
    }


}

/*
public static List<Integer> readIdsOfPeopleWithMultipleApartments(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<Resident> residentRoot = criteriaQuery.from(Resident.class);

        criteriaQuery.select(residentRoot.get("personId").get("id"))
                .groupBy(residentRoot.get("personId"))
                .having(criteriaBuilder.gt(criteriaBuilder.count(residentRoot.get("apartmentId")), 1));

        TypedQuery<Integer> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();


        public static List<Resident> readResidentsWithoutCarEntryPermission(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Resident> criteriaQuery = criteriaBuilder.createQuery(Resident.class);
        Root<Resident> residentRoot = criteriaQuery.from(Resident.class);

        criteriaQuery.select(residentRoot);

        Predicate predicate = criteriaBuilder.equal(residentRoot.get("carEntryPermission"), false);
        criteriaQuery.where(predicate);

        TypedQuery<Resident> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CheckIfElementsExist {
    public static void main(String[] args) {
        // Create an EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("YourPersistenceUnitName");

        // Create an EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // List of flat IDs to check
            List<Integer> flatIds = new ArrayList<>();
            flatIds.add(1);  // Add flat IDs to the list

            // Get the CriteriaBuilder
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            // Create a CriteriaQuery for Flat
            CriteriaQuery<Flat> criteriaQuery = criteriaBuilder.createQuery(Flat.class);
            Root<Flat> root = criteriaQuery.from(Flat.class);

            // Create an "in" predicate to check if id is in the list
            Predicate idPredicate = root.get("id").in(flatIds);

            // Add the predicate to the criteria query
            criteriaQuery.where(idPredicate);

            // Execute the criteria query and get the result list
            List<Flat> matchingFlats = entityManager.createQuery(criteriaQuery).getResultList();

            // Check if matching flats exist
            if (!matchingFlats.isEmpty()) {
                System.out.println("Matching flats exist in the database.");
            } else {
                System.out.println("No matching flats found in the database.");
            }
        } finally {
            // Close the EntityManager and EntityManagerFactory
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}

 */
