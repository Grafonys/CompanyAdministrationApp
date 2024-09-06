package com.homework.company.dao;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDaoImpl {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:6543/homework";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "org.postgresql.Driver";

    @Getter
    private final Connection connection;


    public AbstractDaoImpl() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("POSTGRESQL DRIVER NOT FOUND");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("UNABLE TO CONNECT TO DATABASE");
            throw new RuntimeException(e);
        }
    }
}
