package ru.aston.repository;

import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;

public interface PermissionRepository {

    PermissionDto addPermission(Long permissionId, Long userId);

    UserPermissionDto findPermissionByUserId(Long userId);

    void deletePermission(Long permissionId, Long userId);
}
