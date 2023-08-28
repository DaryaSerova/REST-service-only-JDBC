package ru.aston.service.impl;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.mapper.OrderMapper;
import ru.aston.model.Order;
import ru.aston.repository.OrderRepository;
import ru.aston.repository.impl.OrderRepositoryImpl;
import ru.aston.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
    }

    @Override
    public void createOrder(NewOrderDto newOrderDto) {

        Order order = OrderMapper.toOrder(newOrderDto);
        if (order == null) {
            System.out.println("An empty value cannot be passed.");
        }
        orderRepository.createOrder(order);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {

        Order order = orderRepository.findOrderById(orderId);

        OrderDto orderDto = OrderMapper.toOrderDto(order);

        return null;
    }

    @Override
    public List<OrderDto> getAllOrdersByUserId(Long usId) {
        return null;
    }

    @Override
    public void updateOrder(UpdateOrderDto updateOrderDto, Long orderId, Long userId) {

        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrderById(Long orderId) {

        orderRepository.deleteOrderById(orderId);

    }
}
