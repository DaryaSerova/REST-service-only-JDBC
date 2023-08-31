package ru.aston.repository;

import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.model.User;
import ru.aston.repository.impl.PermissionRepositoryImpl;
import ru.aston.repository.impl.UserRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;


public class PernissionRepositoryTest extends CommonTestcontainer {

    private PermissionRepository permissionRepository = new PermissionRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

    @Test
    public void shouldCreatePermission() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);

        //when
        var result = permissionRepository.addPermission(1L, user.getId());

        //then
        assertNotNull(result.getPermissionId());
        assertTrue(result.getUserId().equals(user.getId()));
    }


    @Test
    public void shouldFindOrder() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        permissionRepository.addPermission(1L, user.getId());

        //when
        var result = permissionRepository.findPermissionByUserId(user.getId());

        //then
        assertNotNull(result);
        assertTrue(result.getPermission().size() == 1);
        assertTrue(result.getPermission().get(0).getId() == 1L);
    }


    @Test
    public void shouldDeletePermission() {
        //given
        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);
        permissionRepository.addPermission(1L, user.getId());
        var userId = user.getId();

        //when
        permissionRepository.deletePermission(1L, user.getId());

        //then
        assertThrows(
                RuntimeException.class,
                () -> permissionRepository.findPermissionByUserId(userId));
    }
}
