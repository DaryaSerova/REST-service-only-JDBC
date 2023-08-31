package ru.aston;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.db.DataBaseConfig;
import ru.aston.util.DbInitializeUtil;

@Testcontainers
public class CommonTestcontainer {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test-db")
            .withUsername("admin")
            .withPassword("admin");


    @BeforeAll
    static void init() {
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
