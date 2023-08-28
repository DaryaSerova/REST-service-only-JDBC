package ru.aston.dto;

import ru.aston.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionDto {

    private String name;

    private List<Permission> permission = new ArrayList<>();


    public UserPermissionDto() {
    }

    public UserPermissionDto(String name, List<Permission> permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

}
