package ru.aston.service;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;

public interface OrderService {

    OrderDto createOrder(NewOrderDto newOrderDto, Long userId);

    OrderDto getOrderById(Long orderId);

    OrderDto updateOrder(UpdateOrderDto updateOrderDto, Long orderId, Long userId);

    void deleteOrderById(Long orderId, Long userId);

}
