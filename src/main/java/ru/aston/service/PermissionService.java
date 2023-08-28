package ru.aston.service;

public interface PermissionService {

    void addPermission(Long permissionId, Long userId);

    void deletePermission(Long permissionId, Long userId);

    void getPermissionsOfAllUsers();

    void getPermissionOfUser(Long usId);
}
