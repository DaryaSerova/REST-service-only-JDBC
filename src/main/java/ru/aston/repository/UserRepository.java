package ru.aston.repository;

import ru.aston.model.User;

import java.util.Optional;

public interface UserRepository {

  User createUser(User user);

  Optional<User> findUserById(Long userId);

  User updateUser(User user);

  void deleteUserById(Long userId);
}
