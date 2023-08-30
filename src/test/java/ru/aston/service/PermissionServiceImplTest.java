package ru.aston.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aston.dto.NewOrderDto;
import ru.aston.repository.OrderRepository;
import ru.aston.repository.PermissionRepository;
import ru.aston.repository.PernissionRepositoryTest;
import ru.aston.service.impl.OrderServiceImpl;
import ru.aston.service.impl.PermissionServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static ru.aston.util.Fixture.*;

public class PermissionServiceImplTest {

    private PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);

    private PermissionService permissionService = new PermissionServiceImpl(permissionRepository);

    @Test
    public void shouldCreatePermission() throws Exception {

        //given
        //when
        doReturn(generatePermissionDto()).when(permissionRepository).addPermission(any(), any());
        var result = permissionService.addPermission(1L, 1L);

        //then
        Assertions.assertNotNull(result.getPermissionId());
        Assertions.assertNotNull(result.getUserId());
    }

    @Test
    public void shouldDeletePermission() throws Exception {

        //given
        var permissionDto = generateUserPermissionDto();
        doReturn(permissionDto).when(permissionRepository).findPermissionByUserId(any());

        //when
        permissionService.deletePermission(1L, 1L);
    }


    @Test
    public void shouldFindPermission() throws Exception {

        //given
        var permissionDto = generateUserPermissionDto();
        doReturn(permissionDto).when(permissionRepository).findPermissionByUserId(any());

        //when
        var result = permissionService.getPermissionOfUser(1L);

        //then
        assertTrue(result.getId().equals(permissionDto.getId()));
        assertTrue(result.getName().equals(permissionDto.getName()));
    }


}
