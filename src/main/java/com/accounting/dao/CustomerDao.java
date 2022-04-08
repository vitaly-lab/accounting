package com.accounting.dao;

import com.accounting.entity.Customer;

import java.util.List;

public interface CustomerDao {

    /**
     * Receives a new instance of {@link Customer} and stores it into database. Sets a generated id to customer.
     *
     * @param customer new instance of customer
     */
    void save(Customer customer);

    /**
     * Returns an {@link Customer} instance by its id
     *
     * @param id customer id in the database
     * @return customer instance
     */
    Customer findById(Long id);

    /**
     * Returns all customers stored in the database.
     *
     * @return account list
     */
    List<Customer> findAll();

    /**
     * Receives stored {@link Customer} instance and updates it in the database
     *
     * @param customer stored customer with updated fields
     */
    void update(Customer customer);

    /**
     * Removes the stored customer from the database.
     *
     * @param customer stored customer instance
     */
    void remove(Customer customer);
}
