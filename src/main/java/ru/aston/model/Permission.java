package ru.aston.model;

public class Permission {

    private Long id;

    private PermissionType type;

    public Permission() {
    }

    public Permission(Long id, PermissionType type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

}
