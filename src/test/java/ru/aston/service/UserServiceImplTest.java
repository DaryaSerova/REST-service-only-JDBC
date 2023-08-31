package ru.aston.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;
import ru.aston.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static ru.aston.util.Fixture.generateUpdateUserDto;
import static ru.aston.util.Fixture.generateUser;

public class UserServiceImplTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void shouldCreateUser() throws Exception {

        //given
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName("Test_name");

        //when
        doReturn(generateUser()).when(userRepository).createUser(any());
        UserDto result = userService.createUser(newUserDto);

        //then
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void shouldThrowExceptionCreateUser() {

        //given
        //when
        //then
        assertThrows(
                RuntimeException.class,
                () -> userService.createUser(null));
    }

    @Test
    public void shouldUpdateUser() throws Exception {

        //given
        UpdateUserDto updateUser = generateUpdateUserDto();
        User user = generateUser();
        doReturn(user).when(userRepository).findUserById(any());
        doReturn(user).when(userRepository).updateUser(any());

        //when
        UserDto result = this.userService.updateUser(updateUser, user.getId());

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));

    }

    @Test
    public void shouldDeleteUser() throws Exception {

        //given
        User user = generateUser();
        doReturn(user).when(userRepository).findUserById(any());

        //when
        this.userService.deleteUserById(user.getId());
    }

    @Test
    public void shouldFindUser() throws Exception {

        //given
        User user = generateUser();
        doReturn(user).when(userRepository).findUserById(any());

        //when
        UserDtoWithOrders result = this.userService.getUserById(user.getId());

        //then
        assertTrue(user.getId().equals(result.getId()));
        assertTrue(user.getName().equals(result.getName()));
    }


}
