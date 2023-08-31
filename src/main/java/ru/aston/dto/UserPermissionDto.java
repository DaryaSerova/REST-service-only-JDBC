package ru.aston.dto;

import ru.aston.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionDto {

    private Long userId;

    private String userName;

    private List<Permission> permission = new ArrayList<>();


    public UserPermissionDto() {
    }

    public UserPermissionDto(Long userId, String userName, List<Permission> permission) {
        this.userId = userId;
        this.userName = userName;
        this.permission = permission;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

}
