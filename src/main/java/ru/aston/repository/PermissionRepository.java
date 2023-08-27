package ru.aston.repository;

public interface PermissionRepository {

    void addPermission(Long permissionId, Long userId);

    void deletePermission(Long permissionId, Long userId);
}
