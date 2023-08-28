package ru.aston.mapper;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.OrderDto;
import ru.aston.model.Order;
import ru.aston.model.User;

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

    public static Order toOrder(NewOrderDto newOrderDto) {

        Order order = new Order();

        if (newOrderDto == null) {
            return null;
        }

        if (newOrderDto.getName() != null) {
            order.setName(newOrderDto.getName());
        }
        if (newOrderDto.getUserId() != null) {
            order.setUserId(newOrderDto.getUserId());
        }

        return order;
    }

    public static OrderDto toOrderDto(Order order) {
    }
}
