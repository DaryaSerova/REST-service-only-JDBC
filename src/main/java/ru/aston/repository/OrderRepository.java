package ru.aston.repository;

import ru.aston.model.Order;

public interface OrderRepository {

    Order createOrder(Order order);

    Order findOrderById(Long orderId);

    Order updateOrder(Order order);

    void deleteOrderById(Long orderId, Long userId);
}
