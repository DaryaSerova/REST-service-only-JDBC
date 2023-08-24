package ru.aston.service;


import ru.aston.model.User;

public interface UserService {

  User createUser(User user);

  User getUserById(Long userId);

  User updateUser(User user);

  void deleteUserById(Long userId);
}
