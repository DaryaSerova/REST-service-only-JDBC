package ru.aston.mapper;

import ru.aston.dto.*;
import ru.aston.model.Order;
import ru.aston.model.Permission;
import ru.aston.model.PermissionType;
import ru.aston.model.User;

import java.sql.ResultSet;

public class PermissionMapper {

    public static UserPermissionDto permissionMap(ResultSet result) throws Exception {

        UserPermissionDto userPermissionDto = new UserPermissionDto();

        while (result.next()) {
            if (userPermissionDto.getId() == null) {
                userPermissionDto.setId(result.getLong("userId"));
                userPermissionDto.setName(result.getString("userName"));
            }
            Permission permission = new Permission();
            permission.setType(PermissionType.valueOf(result.getString("permissionType")));
            userPermissionDto.getPermission().add(permission);

        }
        return userPermissionDto;
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
            if (userDto.getId() == null) {
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
