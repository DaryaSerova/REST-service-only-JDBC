package ru.aston.repository;

import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.model.User;
import ru.aston.repository.impl.UserRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;


public class UserRepositoryTest extends CommonTestcontainer {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Test
    public void shouldCreateUser() {
        //given
        var user = new User();
        user.setName("test");

        //when
        var result = userRepository.createUser(user);

        //then
        assertNotNull(user.getId());
        assertTrue(user.getName().equals(result.getName()));
    }


    @Test
    public void shouldFindUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);

        //when
        var result = userRepository.findUserById(user.getId());

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));
    }

    @Test
    public void shouldUpdateUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);
        user.setName("test2");

        //when
        var result = userRepository.updateUser(user);

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));
    }

    @Test
    public void shouldDeleteUser() {
        //given
        var user = new User();
        user.setName("test");
        user = userRepository.createUser(user);
        var userId = user.getId();

        //when
        userRepository.deleteUserById(user.getId());

        //then
        assertThrows(
                RuntimeException.class,
                () -> userRepository.findUserById(userId));
    }
}
