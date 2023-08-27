package ru.aston.mapper;

import ru.aston.model.Order;
import ru.aston.model.User;

import java.sql.ResultSet;

public class UserMapper {

    public static User userMap(ResultSet result) throws Exception {
        User user = new User();

        while (result.next()) {
            if (user.getId() == null) {
                user.setId(result.getLong("userId"));
                user.setName(result.getString("userName"));
            }
            Order order = new Order();
            order.setName(result.getString("orderName"));
            user.getOrders().add(order);

        }

        return user;
    }
}
