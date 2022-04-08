package com.accounting.dao;

import com.accounting.exception.DatabaseConnectivityException;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector {

    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();

    public DBConnector() {
        ResourceBundle resource = ResourceBundle.getBundle("application");
        dataSource.setServerName(resource.getString("dbcUsername"));
        dataSource.setDatabaseName(resource.getString("accounting"));
        dataSource.setUser(resource.getString("jdbcUsername"));
        dataSource.setPassword(resource.getString("jdbcPassword"));
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseConnectivityException("Database connection could not be obtained", e);
        }
    }
}
