package ru.aston.service.impl;

import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;
import ru.aston.mapper.UserMapper;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;
import ru.aston.repository.impl.UserRepositoryImpl;
import ru.aston.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public UserDto createUser(NewUserDto newUserDto) {

        User user = UserMapper.toUser(newUserDto);
        return UserMapper.toUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDtoWithOrders getUserById(Long userId) {

        return UserMapper.toUserDtoWithOrders(userRepository.findUserById(userId));

    }

    @Override
    public UserDto updateUser(UpdateUserDto updateUserDto, Long userId) {

        User user = userRepository.findUserById(userId);

        UserMapper.mergeToUser(user, updateUserDto);

        return UserMapper.toUserDto(userRepository.updateUser(user));
    }

    @Override
    public void deleteUserById(Long userId) {

        userRepository.findUserById(userId);

        userRepository.deleteUserById(userId);

    }
}
