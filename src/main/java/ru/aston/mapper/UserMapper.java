package ru.aston.mapper;

import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;
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

    public static User toUser(NewUserDto userDto) {

        User user = new User();

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
            return user;
        }

        return null;
    }

    public static UserDto toUserDto(User user) {

        UserDto userDto = new UserDto();

        if (user != null) {
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            return userDto;
        }

        return null;
    }

    public static UserDtoWithOrders toUserDtoWithOrders(User user) {

        UserDtoWithOrders userDto = new UserDtoWithOrders();

        while (user != null) {
            if (user.getId() == null) {
                userDto.setId(user.getId());
                userDto.setName(user.getName());
            }
            Order order = new Order();
            order.setName(user.getOrders().toString());
            userDto.getOrders().add(order);

        }
        return userDto;
    }

    public static void mergeToUser(User user, UpdateUserDto updateUserDto) {
        if (updateUserDto == null) {
            return;
        }

        if (updateUserDto.getName() != null) {
            user.setName(updateUserDto.getName());
        }
    }


}
