package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.mapper.OrderMapper;
import ru.aston.model.Order;
import ru.aston.repository.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderRepositoryImpl implements OrderRepository {

    private final DbConnectionManager dbManager;

    public OrderRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public Order createOrder(Order order) {

        String sqlQuery = "INSERT INTO order_t(name, user_id) VALUES(?, ?);";

        long id = 0;

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, order.getName());
            stmt.setLong(2, order.getUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("Order doesn't create.");
            }
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getLong(1);
                order.setId(id);
                return order;
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return order;
    }

    @Override
    public Order findOrderById(Long orderId) {

        String sqlQuery = "SELECT o.id AS orderId, o.name AS orderName, o.user_id AS userId " +
                "FROM order_t AS o " +
                "WHERE id = ?;";

        Order order;

        try (Connection connect = dbManager.connect(); PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {
            stmt.setLong(1, orderId);
            ResultSet result = stmt.executeQuery();
            order = OrderMapper.orderMap(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return order;
    }

    @Override
    public Order updateOrder(Order order) {

        String sqlQuery = "UPDATE order_t SET name = ? WHERE id = ?;";

        long id = 0;

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, order.getName());
            stmt.setLong(2, order.getId());

            var affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("Order doesn't update.");
            }
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getLong(1);
                order.setId(id);
                return order;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return order;
    }

    @Override
    public void deleteOrderById(Long orderId, Long userId) {

        String sqlQuery = "DELETE FROM order_t WHERE id = ? AND user_id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, orderId);
            stmt.setLong(2, userId);
            var affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("Order doesn't delete.");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
