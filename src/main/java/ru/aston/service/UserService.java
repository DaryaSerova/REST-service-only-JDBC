package ru.aston.service;

import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;

import java.util.List;

public interface UserService {

    void createUser(NewUserDto newUserDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void updateUser(UpdateUserDto updateUserDto, Long userId);

    void deleteUserById(Long userId);


}
