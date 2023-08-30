package ru.aston.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.db.DataBaseConfig;
import ru.aston.model.Order;
import ru.aston.model.User;
import ru.aston.repository.impl.OrderRepositoryImpl;
import ru.aston.repository.impl.PermissionRepositoryImpl;
import ru.aston.repository.impl.UserRepositoryImpl;
import ru.aston.util.DbInitializeUtil;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class PernissionRepositoryTest {

    private PermissionRepository permissionRepository = new PermissionRepositoryImpl();
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
    public void shouldCreatePermission() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);

        //when
        var result = permissionRepository.addPermission(1L, user.getId());

        //then
        assertNotNull(result.getPermissionId());
        assertTrue(result.getUserId().equals(user.getId()));
    }


    @Test
    public void shouldFindOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        permissionRepository.addPermission(1L, user.getId());

        //when
        var result = permissionRepository.findPermissionByUserId(user.getId());

        //then
        assertNotNull(result);
        assertTrue(result.getPermission().size() == 1);
        assertTrue(result.getPermission().get(0).getId() == 1L);
    }


    @Test
    public void shouldDeletePermission() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        permissionRepository.addPermission(1L, user.getId());
        var userId = user.getId();

        //when
        permissionRepository.deletePermission(1L, user.getId());

        //then
        assertThrows(
                RuntimeException.class,
                () -> permissionRepository.findPermissionByUserId(userId));
    }
}
