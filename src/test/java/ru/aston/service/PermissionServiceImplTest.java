package ru.aston.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.repository.PermissionRepository;
import ru.aston.service.impl.PermissionServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static ru.aston.util.Fixture.generatePermissionDto;
import static ru.aston.util.Fixture.generateUserPermissionDto;

public class PermissionServiceImplTest {

    private PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);

    private PermissionService permissionService = new PermissionServiceImpl(permissionRepository);

    @Test
    public void shouldAddPermission() throws Exception {

        //given
        //when
        doReturn(generatePermissionDto()).when(permissionRepository).addPermission(any(), any());
        PermissionDto result = permissionService.addPermission(1L, 1L);

        //then
        Assertions.assertNotNull(result.getPermissionId());
        Assertions.assertNotNull(result.getUserId());
    }

    @Test
    public void shouldFindPermission() throws Exception {

        //given
        UserPermissionDto userPermissionDto = generateUserPermissionDto();
        doReturn(userPermissionDto).when(permissionRepository).findPermissionByUserId(any());

        //when
        UserPermissionDto result = permissionService.getPermissionOfUser(1L);

        //then
        assertTrue(result.getPermission().get(0).equals(userPermissionDto.getPermission().get(0)));
        assertTrue(result.getUserId().equals(userPermissionDto.getUserId()));
    }


    @Test
    public void shouldDeletePermission() throws Exception {

        //given
        UserPermissionDto userPermissionDto = generateUserPermissionDto();
        doReturn(userPermissionDto).when(permissionRepository).findPermissionByUserId(any());

        //when
        permissionService.deletePermission(1L, 1L);
    }

}
