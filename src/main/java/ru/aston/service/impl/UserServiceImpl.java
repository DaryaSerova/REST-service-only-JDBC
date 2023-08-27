package ru.aston.service.impl;

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
    public void createUser(User user) {

        userRepository.createUser(user);

    }

    @Override
    public User getUserById(Long userId) {

        var resultUser = userRepository.findUserById(userId);

        return resultUser;
    }

    @Override
    public void updateUser(User user) {

        userRepository.updateUser(user);
    }

    @Override
    public void deleteUserById(Long userId) {

        userRepository.deleteUserById(userId);

    }
}
