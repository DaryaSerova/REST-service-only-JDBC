package ru.aston.dto;

import ru.aston.model.Order;

import java.util.ArrayList;
import java.util.List;

public class UserDtoWithOrders {

    private Long id;

    private String name;

    private List<Order> orders = new ArrayList<>();

    public UserDtoWithOrders() {
    }

    public UserDtoWithOrders(Long id, String name, List<Order> orders) {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
