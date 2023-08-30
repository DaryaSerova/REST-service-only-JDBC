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
import ru.aston.repository.impl.UserRepositoryImpl;
import ru.aston.util.DbInitializeUtil;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class OrderRepositoryTest {

    private OrderRepository orderRepository = new OrderRepositoryImpl();
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
    public void shouldCreateOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        Order order = new Order();
        order.setName("test_order");
        order.setUserId(user.getId());

        //when
        var result = orderRepository.createOrder(order);

        //then
        assertNotNull(order.getId());
        assertTrue(order.getName().equals(result.getName()));
    }


    @Test
    public void shouldFindOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        Order order = new Order();
        order.setName("test_order");
        order.setUserId(user.getId());
        order = orderRepository.createOrder(order);

        //when
        Order result = orderRepository.findOrderById(order.getId());

        //then
        assertTrue(order.getId().equals(result.getId()));
        assertTrue(order.getName().equals(result.getName()));
    }

    @Test
    public void shouldUpdateOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        Order order = new Order();
        order.setName("test_order");
        order.setUserId(user.getId());
        order = orderRepository.createOrder(order);
        order.setName("update_name");

        //when
        Order result = orderRepository.updateOrder(order);

        //then
        assertTrue(order.getId().equals(result.getId()));
        assertTrue(order.getName().equals(result.getName()));
    }

    @Test
    public void shouldDeleteOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        Order order = new Order();
        order.setName("test_order");
        order.setUserId(1L);
        order.setUserId(user.getId());
        order = orderRepository.createOrder(order);
        Long orderId = order.getId();

        //when
        orderRepository.deleteOrderById(order.getId(), order.getUserId());

        //then
        assertThrows(
                RuntimeException.class,
                () -> orderRepository.findOrderById(orderId));
    }
}
