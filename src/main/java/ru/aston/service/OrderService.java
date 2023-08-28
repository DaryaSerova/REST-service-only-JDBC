package ru.aston.service;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;

import java.util.List;

public interface OrderService {

    void createOrder(NewOrderDto newOrderDto, Long userId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrdersByUserId(Long usId);

    void updateOrder(UpdateOrderDto updateOrderDto, Long orderId, Long userId);

    void deleteOrderById(Long orderId, Long userId);

}
