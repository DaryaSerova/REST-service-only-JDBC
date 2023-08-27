package ru.aston.service.impl;

import ru.aston.repository.PermissionRepository;
import ru.aston.repository.impl.PermissionRepositoryImpl;
import ru.aston.service.PermissionService;

public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;

    public PermissionServiceImpl() {
        this.permissionRepository = new PermissionRepositoryImpl();
    }


    @Override
    public void addPermission(Long permissionId, Long userId) {

        permissionRepository.addPermission(permissionId, userId);

    }

    @Override
    public void deletePermission(Long permissionId, Long userId) {

        permissionRepository.deletePermission(permissionId, userId);

    }
}
