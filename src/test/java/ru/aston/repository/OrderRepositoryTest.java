package ru.aston.repository;

import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.model.Order;
import ru.aston.model.User;
import ru.aston.repository.impl.OrderRepositoryImpl;
import ru.aston.repository.impl.UserRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest extends CommonTestcontainer {

    private OrderRepository orderRepository = new OrderRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

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
