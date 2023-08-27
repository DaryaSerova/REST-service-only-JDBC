package ru.aston.service.impl;

import ru.aston.model.Order;
import ru.aston.repository.OrderRepository;
import ru.aston.repository.impl.OrderRepositoryImpl;
import ru.aston.service.OrderService;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
    }

    @Override
    public void createOrder(Order order) {

        orderRepository.createOrder(order);

    }

    @Override
    public Order getOrderById(Long orderId) {

        var resultOrder = orderRepository.findOrderById(orderId);

        return resultOrder;
    }

    @Override
    public void updateOrder(Order order) {

        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrderById(Long orderId) {

        orderRepository.deleteOrderById(orderId);

    }
}
