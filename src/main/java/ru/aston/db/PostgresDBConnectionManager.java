package ru.aston.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.aston.util.DbInitializeUtil.*;

public class PostgresDBConnectionManager implements DbConnectionManager {

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}