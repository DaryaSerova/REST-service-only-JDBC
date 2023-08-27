package ru.aston.mapper;

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
}
