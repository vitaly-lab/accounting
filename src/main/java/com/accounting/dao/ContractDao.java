package com.accounting.dao;

import com.accounting.entity.Contract;

import java.util.List;

public interface ContractDao {

    /**
     * Receives a new instance of {@link Contract} and stores it into database. Sets a generated id to contract.
     *
     * @param contract new instance of contract
     */
    void save(Contract contract);

    /**
     * Returns an {@link Contract} instance by its id
     *
     * @param id contract id in the database
     * @return contract instance
     */
    Contract findById(Long id);

    /**
     * Returns all contracts stored in the database.
     *
     * @return contract list
     */
    List<Contract> findAll();

    /**
     * Receives stored {@link Contract} instance and updates it in the database
     *
     * @param contract stored contract with updated fields
     */
    void update(Contract contract);

    /**
     * Removes the stored contract from the database.
     *
     * @param contract stored contract instance
     */
    void remove(Contract contract);
}
