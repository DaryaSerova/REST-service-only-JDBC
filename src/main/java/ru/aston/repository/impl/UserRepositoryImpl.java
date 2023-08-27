package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.mapper.UserMapper;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepositoryImpl implements UserRepository {

    private final DbConnectionManager dbManager;

    public UserRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public void createUser(User user) {

        String sqlQuery = "INSERT INTO user_t(name) VALUES(?);";

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setString(1, user.getName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User successfully create.");
            } else {
                System.out.println("User doesn't create.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public User findUserById(Long userId) {

        String sqlQuery = "SELECT u.id AS userId, u.name AS userName, o.name AS orderName " +
                "FROM user_t AS u " +
                "LEFT JOIN order_t AS o ON u.id = o.user_id WHERE id = ?;";

        User user = null;

        try (Connection connect = dbManager.connect(); PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, user.getId());

            ResultSet result = stmt.executeQuery();

            if (!result.first()) {
                String.format("User with id = %s was not found.", userId);
            }

            user = UserMapper.userMap(result);

            return user;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    @Override
    public void updateUser(User user) {

        String sqlQuery = "UPDATE user_t SET name = ? WHERE id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getId());
            var affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User successfully update.");
            } else {
                System.out.println("User doesn't update.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void deleteUserById(Long userId) {
        String sqlQuery = "DELETE FROM user_t WHERE id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, userId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User successfully delete.");
            } else {
                System.out.println("User doesn't delete.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
