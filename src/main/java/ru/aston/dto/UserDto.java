package ru.aston.dto;

import ru.aston.model.Order;
import ru.aston.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;

    private String name;

    private List<Permission> permission = new ArrayList<>();

    private List<Order> orders = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
