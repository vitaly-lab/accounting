package com.accounting.dao;

import com.accounting.entity.Contract;
import com.accounting.exception.DaoOperationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ContractDaoImpl implements ContractDao {

    private EntityManagerFactory emf;

    public ContractDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Contract contract) {
        performWithinPersistenceContext(em -> em.persist(contract));
    }

    @Override
    public Contract findById(Long id) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Contract.class, id));
    }

    @Override
    public List<Contract> findAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select a from Contract a", Contract.class).getResultList());
    }

    @Override
    public void update(Contract contract) {
        performWithinPersistenceContext(em -> em.merge(contract));
    }

    @Override
    public void remove(Contract contract) {
        performWithinPersistenceContext(entityManager -> {
            Contract mergedContract = entityManager.merge(contract);
            entityManager.remove(mergedContract);
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
