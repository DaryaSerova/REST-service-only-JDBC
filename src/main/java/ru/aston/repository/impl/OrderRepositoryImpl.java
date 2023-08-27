package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.mapper.OrderMapper;
import ru.aston.model.Order;
import ru.aston.repository.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderRepositoryImpl implements OrderRepository {

    private final DbConnectionManager dbManager;

    public OrderRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public void createOrder(Order order) {

        String sqlQuery = "INSERT INTO order_t(name, user_id) VALUES(?, ?);";

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setString(1, order.getName());
            stmt.setLong(2, order.getUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Order successfully create.");
            } else {
                System.out.println("Order doesn't create.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Order findOrderById(Long orderId) {

        String sqlQuery = "SELECT o.id AS orderId, o.name AS orderName, o.user_id AS userId " +
                "FROM order_t AS o " +
                "WHERE id = ?;";

        Order order = null;

        try (Connection connect = dbManager.connect(); PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, order.getId());

            ResultSet result = stmt.executeQuery();

            if (!result.first()) {
                String.format("Order with id = %s was not found.", orderId);
            }

            order = OrderMapper.orderMap(result);

            return order;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    @Override
    public void updateOrder(Order order) {

        String sqlQuery = "UPDATE order_t SET name = ? WHERE id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setString(1, order.getName());
            stmt.setLong(2, order.getId());
            var affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Order successfully update.");
            } else {
                System.out.println("Order doesn't update.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void deleteOrderById(Long orderId) {
        String sqlQuery = "DELETE FROM order_t WHERE id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, orderId);
            var affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Order successfully delete.");
            } else {
                System.out.println("Order doesn't delete.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
