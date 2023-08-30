package ru.aston.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDBConnectionManager implements DbConnectionManager {

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DataBaseConfig.getUrl(), DataBaseConfig.getUser(),
                DataBaseConfig.getPass());

    }
}