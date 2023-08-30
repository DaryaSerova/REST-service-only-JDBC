package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.mapper.UserMapper;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserRepositoryImpl implements UserRepository {

    private final DbConnectionManager dbManager;

    public UserRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public User createUser(User user) {

        String sqlQuery = "INSERT INTO user_t(name) VALUES(?);";

        long id = 0;

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("User doesn't create.");
            }
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getLong(1);
                user.setId(id);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return user;
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
            user = UserMapper.userMap(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return user;
    }

    @Override
    public User updateUser(User user) {

        String sqlQuery = "UPDATE user_t SET name = ? WHERE id = ?;";

        long id = 0;

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("User doesn't update.");
            }
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getLong(1);
                user.setId(id);
                return user;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return user;
    }

    @Override
    public void deleteUserById(Long userId) {
        String sqlQuery = "DELETE FROM user_t WHERE id = ?;";

        try (Connection connect = dbManager.connect();
             PreparedStatement stmt = connect.prepareStatement(sqlQuery)) {

            stmt.setLong(1, userId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows <=0) {
                throw new RuntimeException("User doesn't delete.");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
