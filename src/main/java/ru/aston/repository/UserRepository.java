package ru.aston.repository;

import ru.aston.model.User;

public interface UserRepository {

    void createUser(User user);

    User findUserById(Long userId);

    void updateUser(User user);

    void deleteUserById(Long userId);
}
