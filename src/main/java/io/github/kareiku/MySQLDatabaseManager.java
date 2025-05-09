package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseManager extends DatabaseManager {
    private final String mysqlUrl;

    public MySQLDatabaseManager(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    @Override
    protected @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.mysqlUrl);
    }
}
