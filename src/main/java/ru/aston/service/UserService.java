package ru.aston.service;

import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;

public interface UserService {

    UserDto createUser(NewUserDto newUserDto);

    UserDtoWithOrders getUserById(Long userId);

    UserDto updateUser(UpdateUserDto updateUserDto, Long userId);

    void deleteUserById(Long userId);


}
