package ru.aston.util;

import ru.aston.dto.PermissionDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.model.Order;
import ru.aston.model.Permission;
import ru.aston.model.PermissionType;
import ru.aston.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Fixture {

    public static User generateUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Test_name");
        return user;
    }

    public static Order generateOrder() {

        Order order = new Order();
        order.setId(1L);
        order.setName("Test_name");
        order.setId(1L);
        return order;
    }

    public static UpdateUserDto generateUpdateUserDto() {

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setName("Update_name");
        return updateUserDto;
    }

    public static PermissionDto generatePermissionDto() {

        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setUserId(1L);
        permissionDto.setPermissionId(1L);
        return permissionDto;
    }

    public static UpdateOrderDto generateUpdateOrderDto() {

        UpdateOrderDto updateOrderDto = new UpdateOrderDto();
        updateOrderDto.setName("Update_name");
        return updateOrderDto;
    }

    public static UserPermissionDto generateUserPermissionDto() {

        UserPermissionDto userPermissionDto = new UserPermissionDto();
        userPermissionDto.setUserId(1L);
        userPermissionDto.setUserName("Test_name");
        userPermissionDto.setPermission(Arrays.asList(new Permission(2L, PermissionType.READ)));
        return userPermissionDto;
    }
}
