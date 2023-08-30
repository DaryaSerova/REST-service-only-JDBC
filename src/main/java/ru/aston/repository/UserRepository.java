package ru.aston.repository;

import ru.aston.model.User;

public interface UserRepository {

    User createUser(User user);

    User findUserById(Long userId);

    User updateUser(User user);

    void deleteUserById(Long userId);

}
