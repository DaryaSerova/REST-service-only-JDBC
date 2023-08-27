package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.repository.PermissionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PermissionRepositoryImpl implements PermissionRepository {

    private final DbConnectionManager dbManager;

    public PermissionRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public void addPermission(Long permissionId, Long userId) {

        String querySql = "INSERT INTO user_permission_t(permission_id, user_id) VALUES(?, ?);";

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(querySql)) {

            stmt.setLong(1, permissionId);
            stmt.setLong(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                String.format("Permission for user with id = %s successfully add.", userId);
            } else {
                String.format("Permission for user with id = %s doesn't add.", userId);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void deletePermission(Long permissionId, Long userId) {

        String querySql = "DELETE FROM user_permission_t WHERE user_id = ? AND permission_id = ?;";

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(querySql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, permissionId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Permission successfully delete.");
            } else {
                System.out.println("Permission doesn't delete.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
