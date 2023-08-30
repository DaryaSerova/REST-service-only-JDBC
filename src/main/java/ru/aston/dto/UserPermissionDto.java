package ru.aston.dto;

import ru.aston.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionDto {

    private Long id;

    private String name;

    private List<Permission> permission = new ArrayList<>();


    public UserPermissionDto() {
    }

    public UserPermissionDto(Long id, String name, List<Permission> permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

}
