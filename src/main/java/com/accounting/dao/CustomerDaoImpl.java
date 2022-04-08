package com.accounting.dao;

import com.accounting.entity.Customer;
import com.accounting.exception.DaoOperationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerDaoImpl implements CustomerDao {

    private EntityManagerFactory emf;

    public CustomerDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Customer customer) {
        performWithinPersistenceContext(em -> em.persist(customer));
    }

    @Override
    public Customer findById(Long id) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> findAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select customers from Customer customers", Customer.class).getResultList());
    }

    @Override
    public void update(Customer customer) {
        performWithinPersistenceContext(em -> em.merge(customer));
    }

    @Override
    public void remove(Customer customer) {
        performWithinPersistenceContext(entityManager -> {
            Customer mergedCustomer = entityManager.merge(customer);
            entityManager.remove(mergedCustomer);
        });
    }

    private void performWithinPersistenceContext(Consumer<EntityManager> entityManagerConsumer) {
        performReturningWithinPersistenceContext(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }

    private <T> T performReturningWithinPersistenceContext(Function<EntityManager, T> entityManagerFunction) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        T result;
        try {
            result = entityManagerFunction.apply(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DaoOperationException("Error performing dao operation. Transaction is rolled back!", e);
        } finally {
            entityManager.close();
        }
        return result;
    }
}
