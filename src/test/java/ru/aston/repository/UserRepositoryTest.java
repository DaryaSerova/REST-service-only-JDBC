package ru.aston.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.db.DataBaseConfig;
import ru.aston.model.User;
import ru.aston.repository.impl.UserRepositoryImpl;
import ru.aston.util.DbInitializeUtil;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepositoryImpl();
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

    @Test
    public void shouldCreateUser() {
        //given
        var user = new User();
        user.setName("test");

        //when
        var result = userRepository.createUser(user);

        //then
        assertNotNull(user.getId());
        assertTrue(user.getName().equals(result.getName()));
    }


    @Test
    public void shouldFindUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);

        //when
        var result = userRepository.findUserById(user.getId());

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));
    }

    @Test
    public void shouldUpdateUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);
        user.setName("test2");

        //when
        var result = userRepository.updateUser(user);

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));
    }

    @Test
    public void shouldDeleteUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);
        var userId = user.getId();

        //when
        userRepository.deleteUserById(user.getId());

        //then
        assertThrows(
                RuntimeException.class,
                () -> userRepository.findUserById(userId));
    }
}
