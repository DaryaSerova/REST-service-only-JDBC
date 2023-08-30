package ru.aston.service.impl;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.mapper.OrderMapper;
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
    public OrderDto createOrder(NewOrderDto newOrderDto, Long userId) {

        if (newOrderDto == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        if (userId == null) {
            throw new RuntimeException("UserId cannot be empty.");
        }

        Order order = OrderMapper.toOrder(newOrderDto, userId);
        if (order == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        return OrderMapper.toOrderDto(orderRepository.createOrder(order));
    }

    @Override
    public OrderDto getOrderById(Long orderId) {

        return OrderMapper.toOrderDto(orderRepository.findOrderById(orderId));
    }

    @Override
    public OrderDto updateOrder(UpdateOrderDto updateOrderDto, Long orderId, Long userId) {

        var order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order does not exist");
        } else if (order.getUserId() != userId) {
            throw new RuntimeException(
                    String.format("Order with id = %s does not belong to user with id = %s.", orderId, userId));
        }
        OrderMapper.mergeToOrder(order, updateOrderDto);
        return OrderMapper.toOrderDto(orderRepository.updateOrder(order));
    }

    @Override
    public void deleteOrderById(Long orderId, Long userId) {

        var order = orderRepository.findOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order does not exist");
        } else if (order.getUserId() != userId) {
            throw new RuntimeException(
                    String.format("Order with id = %s does not belong to user with id = %s.", orderId, userId));
        }
        orderRepository.deleteOrderById(orderId, userId);

    }
}
