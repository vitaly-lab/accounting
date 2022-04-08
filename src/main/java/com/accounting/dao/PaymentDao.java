package com.accounting.dao;

import com.accounting.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDao {

    /**
     * Receives a new instance of {@link Payment} and stores it into database. Sets a generated id to payment.
     *
     * @param payment new instance of payment
     */
    void save(Payment payment);

    /**
     * Returns an {@link Payment} instance by its id
     *
     * @param id payment id in the database
     * @return payment instance
     */
    Payment findById(Long id);

    /**
     * Returns all payments stored in the database.
     *
     * @return payment list
     */
    List<Payment> findAll();

    /**
     * Receives stored {@link Payment} instance and updates it in the database
     *
     * @param payment stored payment with updated fields
     */
    void update(Payment payment);

    /**
     * Removes the stored payment from the database.
     *
     * @param payment stored payment instance
     */
    void remove(Payment payment);

    /**
     * Returns {@link Payment} instance by its contractId
     *
     * @param contractId payment contractIds
     * @return contractId instance
     */
    List<Payment> findAllByContract(Long contractId);

    /**
     * Returns {@link Payment} instance by its customerId
     *
     * @param customerId payment customerIds
     * @return customerId instance
     */
    List<Payment> findAllByCustomer(Long customerId);

    /**
     * Returns {@link Payment} instance by its customerId
     * Returns {@link Payment} instance by its amount
     *
     * @param customerId payment customerIds
     * @param amount     payment customerIds
     * @return customerId instance
     * @return amount instance
     */
    List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount);
}
