package ru.aston.repository;

import ru.aston.model.Order;

public interface OrderRepository {

    void createOrder(Order order);

    Order findOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrderById(Long orderId);
}
