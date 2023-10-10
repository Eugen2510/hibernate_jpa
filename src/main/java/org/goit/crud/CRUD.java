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

        EntityTransaction transaction = null;

        try (EntityManager manager = factory.createEntityManager()){
            transaction = manager.getTransaction();
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
        manager.close();
        return byId;
    }


    public <T extends MarkEntities>  void updateEntity(T entity, int id){

        EntityTransaction transaction = null;
        try (EntityManager manager = factory.createEntityManager()) {
            transaction = manager.getTransaction();
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

