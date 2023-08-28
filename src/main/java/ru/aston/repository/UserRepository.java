package ru.aston.repository;

import ru.aston.model.User;

import java.util.List;

public interface UserRepository {

    void createUser(User user);

    User findUserById(Long userId);

    List<User> findAllUsers();

    void updateUser(User user);

    void deleteUserById(Long userId);

}
