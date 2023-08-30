package ru.aston.service.impl;

import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.repository.PermissionRepository;
import ru.aston.repository.impl.PermissionRepositoryImpl;
import ru.aston.service.PermissionService;

public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;

    public PermissionServiceImpl() {
        this.permissionRepository = new PermissionRepositoryImpl();
    }


    @Override
    public PermissionDto addPermission(Long permissionId, Long userId) {

        return permissionRepository.addPermission(permissionId, userId);

    }

    @Override
    public UserPermissionDto getPermissionOfUser(Long userId) {

        return permissionRepository.findPermissionOfUser(userId);
    }

    @Override
    public void deletePermission(Long permissionId, Long userId) {

        permissionRepository.findPermissionOfUser(userId);
        permissionRepository.deletePermission(permissionId, userId);

    }
}
