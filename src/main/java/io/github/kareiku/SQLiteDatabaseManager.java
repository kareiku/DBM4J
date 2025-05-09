package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class SQLiteDatabaseManager extends DatabaseManager {
    private static final String JDBC_SQLITE_URL_PREFIX = "jdbc:sqlite:";
    private final String sqliteDatabaseName;

    public SQLiteDatabaseManager(String sqliteDatabaseName) {
        this.sqliteDatabaseName = sqliteDatabaseName;
    }

    @Override
    protected @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_SQLITE_URL_PREFIX + Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(this.sqliteDatabaseName)).getPath());
    }
}
