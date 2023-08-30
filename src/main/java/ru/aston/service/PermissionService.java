package ru.aston.service;

import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;

public interface PermissionService {

    PermissionDto addPermission(Long permissionId, Long userId);

    UserPermissionDto getPermissionOfUser(Long usId);

    void deletePermission(Long permissionId, Long userId);
}
