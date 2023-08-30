package ru.aston.util;

import ru.aston.db.DataBaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.aston.db.InitStatement.*;

public class DbInitializeUtil {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";


    public static void initDB() {
        Connection conn = null;
        Statement stmt;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DataBaseConfig.getUrl(), DataBaseConfig.getUser(),
                    DataBaseConfig.getPass());
            stmt = conn.createStatement();

            stmt.executeUpdate(DROP_ORDER_TABLE_STATEMENT);
            stmt.executeUpdate(DROP_USER_PERMISSION_TABLE_STATEMENT);
            stmt.executeUpdate(DROP_PERMISSION_TABLE_STATEMENT);
            stmt.executeUpdate(DROP_USER_TABLE_STATEMENT);
            stmt.executeUpdate(CREATE_PERMISSION_TABLE_STATEMENT);
            stmt.executeUpdate(CREATE_USER_PERMISSION_TABLE_STATEMENT);
            stmt.executeUpdate(CREATE_USER_TABLE_STATEMENT);
            stmt.executeUpdate(CREATE_ORDER_TABLE_STATEMENT);
            stmt.executeUpdate(INSERT_PERMISSION_TYPE_STATEMENT);

            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

