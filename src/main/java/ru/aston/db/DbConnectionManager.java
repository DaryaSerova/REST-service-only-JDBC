package ru.aston.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnectionManager {
  Connection connect() throws SQLException;

  }
