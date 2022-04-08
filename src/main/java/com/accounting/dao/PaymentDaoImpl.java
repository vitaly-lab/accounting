package com.accounting.dao;

import com.accounting.entity.Payment;
import com.accounting.exception.DaoOperationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class PaymentDaoImpl implements PaymentDao {

    private EntityManagerFactory emf;

    public PaymentDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Payment payment) {
        performWithinPersistenceContext(em -> em.persist(payment));
    }

    @Override
    public Payment findById(Long id) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Payment.class, id));
    }

    @Override
    public List<Payment> findAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select a from Payment a", Payment.class).getResultList());
    }

    @Override
    public void update(Payment payment) {
        performWithinPersistenceContext(em -> em.merge(payment));
    }

    @Override
    public void remove(Payment payment) {
        performWithinPersistenceContext(entityManager -> {
            Payment mergedPayment = entityManager.merge(payment);
            entityManager.remove(mergedPayment);
        });
    }

    @Override
    public List<Payment> findAllByContract(Long contractId) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select a from Payment a where a.contractId = :contractId", Payment.class).getResultList());
    }

    @Override
    public List<Payment> findAllByCustomer(Long customerId) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select a from Payment a where a.customerId = :customerId", Payment.class).getResultList());
    }

    @Override
    public List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select a.customerId from Payment a where a.amount < :amount", Payment.class).getResultList());
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
