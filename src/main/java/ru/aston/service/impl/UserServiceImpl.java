package ru.aston.service.impl;

import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.mapper.UserMapper;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;
import ru.aston.repository.impl.UserRepositoryImpl;
import ru.aston.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public void createUser(NewUserDto newUserDto) {

        User user = UserMapper.toUser(newUserDto);
        userRepository.createUser(user);

    }

    @Override
    public UserDto getUserById(Long userId) {

        return UserMapper.toUserDto(userRepository.findUserById(userId));

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAllUsers();

        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getOrders()))
                .collect(Collectors.toList());

    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto, Long userId) {

        User user = userRepository.findUserById(userId);

        UserMapper.mergeToUser(user, updateUserDto);

        userRepository.updateUser(user);
    }

    @Override
    public void deleteUserById(Long userId) {

        getUserById(userId);

        userRepository.deleteUserById(userId);

    }
}
