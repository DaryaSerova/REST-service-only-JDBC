package ru.aston.repository.impl;

import ru.aston.db.DbConnectionManager;
import ru.aston.db.PostgresDBConnectionManager;
import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.mapper.PermissionMapper;
import ru.aston.repository.PermissionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PermissionRepositoryImpl implements PermissionRepository {

    private final DbConnectionManager dbManager;

    public PermissionRepositoryImpl() {
        this.dbManager = new PostgresDBConnectionManager();
    }

    @Override
    public PermissionDto addPermission(Long permissionId, Long userId) {

        String querySql = "INSERT INTO user_permission_t(permission_id, user_id) VALUES(?, ?);";

        PermissionDto permissionDto = new PermissionDto();

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(querySql)) {

            stmt.setLong(1, permissionId);
            stmt.setLong(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException(String.format("Permission for user with id = %s doesn't add.", userId));
            }
            permissionDto.setPermissionId(permissionId);
            permissionDto.setUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return permissionDto;
    }

    @Override
    public UserPermissionDto findPermissionByUserId(Long userId) {

        String sqlQuery = "SELECT usperm.user_id AS userId, u.name AS userName, perm.type AS permissionType , perm.id AS permissionId " +
                "FROM user_permission_t AS usperm " +
                "LEFT JOIN user_t AS u ON usperm.user_id = u.id " +
                "LEFT JOIN permission_t AS perm ON usperm.permission_id = perm.id " +
                "WHERE usperm.user_id = ?;";

        UserPermissionDto userPermissionDto;

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setLong(1, userId);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                throw new RuntimeException(String.format("User with id = %s have not permission.", userId));
            }

            userPermissionDto = PermissionMapper.permissionMap(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return userPermissionDto;
    }

    @Override
    public void deletePermission(Long permissionId, Long userId) {

        String sqlQuery = "DELETE FROM user_permission_t WHERE permission_id = ? AND user_id = ?;";

        try (Connection connection = dbManager.connect();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setLong(1, permissionId);
            stmt.setLong(2, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                throw new RuntimeException("Permission doesn't delete.");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
