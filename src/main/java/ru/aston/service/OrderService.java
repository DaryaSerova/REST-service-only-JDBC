package ru.aston.service;

import ru.aston.model.Order;

public interface OrderService {

    void createOrder(Order order);

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrderById(Long orderId);
}
