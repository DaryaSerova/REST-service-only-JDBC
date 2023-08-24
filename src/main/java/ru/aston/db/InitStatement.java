package ru.aston.db;

public class InitStatement {

  public static final String DROP_USER_TABLE_STATEMENT = "DROP TABLE IF EXISTS USER_T;";
  public static final String CREATE_USER_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS USER_T " +
          "(ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, " +
          " name CHARACTER VARYING (255), " +
          " CONSTRAINT USER_T_PK PRIMARY KEY (ID));";

  public static final String DROP_ORDER_TABLE_STATEMENT = "DROP TABLE IF EXISTS ORDER_T;";
  public static final String CREATE_ORDER_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS ORDER_T " +
          "(ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, " +
          "NAME CHARACTER VARYING (255), " +
          "USER_ID BIGINT NOT NULL, " +
          "CONSTRAINT ORDER_T_PK PRIMARY KEY (ID), " +
          "CONSTRAINT ORDER_T_FK FOREIGN KEY (USER_ID) REFERENCES USER_T(ID) ON delete CASCADE ON update CASCADE);";

  public static final String DROP_PERMISSION_TABLE_STATEMENT = "DROP TABLE IF EXISTS PERMISSION_T;";
  public static final String CREATE_PERMISSION_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS PERMISSION_T " +
          "(ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, " +
          "name CHARACTER VARYING (255), " +
          "code CHARACTER VARYING (255), " +
          "CONSTRAINT PERMISSION_T_PK PRIMARY KEY (ID));";

  public static final String DROP_USER_PERMISSION_TABLE_STATEMENT = "DROP TABLE IF EXISTS USER_PERMISSION_T;";
  public static final String CREATE_USER_PERMISSION_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS USER_PERMISSION_T " +
          "(user_id BIGINT NOT NULL, " +
          "permission_id BIGINT NOT NULL );";
}
