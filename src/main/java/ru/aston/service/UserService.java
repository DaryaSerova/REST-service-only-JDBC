package ru.aston.service;


import ru.aston.model.User;

public interface UserService {

    void createUser(User user);

    User getUserById(Long userId);

    void updateUser(User user);

    void deleteUserById(Long userId);
}
