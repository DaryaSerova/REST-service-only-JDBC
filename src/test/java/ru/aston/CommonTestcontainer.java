package ru.aston;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.db.DataBaseConfig;
import ru.aston.util.DbInitializeUtil;
import ru.aston.util.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;


@Testcontainers
public class CommonTestcontainer {
    public static Properties properties = PropertiesLoader.loadProperties("application.properties");

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName(properties.getProperty("db.name"))
            .withUsername(properties.getProperty("db.username"))
            .withPassword(properties.getProperty("db.password"));


    @BeforeAll
    static void init() throws IOException {
        postgreSQLContainer.start();
        DataBaseConfig.setUrl(postgreSQLContainer.getJdbcUrl());
        DataBaseConfig.setUser(postgreSQLContainer.getUsername());
        DataBaseConfig.setPass(postgreSQLContainer.getPassword());
        DbInitializeUtil.initDB();
    }

    @AfterAll
    static void destroy() {
        postgreSQLContainer.stop();
    }

}
