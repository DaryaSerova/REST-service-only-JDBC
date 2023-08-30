package ru.aston.mapper;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.model.Order;

import java.sql.ResultSet;

public class OrderMapper {

    public static Order orderMap(ResultSet result) throws Exception {

        if (result.next()) {
            Order order = new Order();
            order.setId(result.getLong("orderId"));
            order.setName(result.getString("orderName"));
            order.setUserId(result.getLong("userId"));
            return order;
        }
        return null;
    }

    public static Order toOrder(NewOrderDto newOrderDto, Long userId) {

        if (userId == null && newOrderDto == null) {
            return null;
        }

        Order order = new Order();

        if (newOrderDto != null) {
            order.setName(newOrderDto.getName());
            order.setUserId(userId);
        }

        return order;
    }

    public static OrderDto toOrderDto(Order order) {

        if (order == null) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        if (order != null) {
            orderDto.setId(order.getId());
            orderDto.setName(order.getName());
            orderDto.setUserId(order.getUserId());
        }
        return orderDto;
    }

    public static void mergeToOrder(Order order, UpdateOrderDto updateOrderDto) {
        if (updateOrderDto == null) {
            return;
        }

        if (updateOrderDto.getName() != null) {
            order.setName(updateOrderDto.getName());
        }
    }
}
