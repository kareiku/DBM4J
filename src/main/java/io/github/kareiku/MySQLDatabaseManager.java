package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A concrete implementation of {@link DatabaseManager} for interacting with a MySQL database.
 * <p>
 * This class connects to a MySQL database using the provided JDBC URL.
 */
public class MySQLDatabaseManager extends DatabaseManager {
    private final String mysqlUrl;

    /**
     * Constructs a new {@link MySQLDatabaseManager} instance.
     *
     * @param mysqlUrl the JDBC URL for the MySQL database connection
     */
    public MySQLDatabaseManager(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    /**
     * Establishes a connection to the MySQL database using the provided JDBC URL.
     *
     * @return a non-null {@link Connection} to the MySQL database
     * @throws SQLException if a database access error occurs while establishing the connection
     */
    @Override
    protected @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.mysqlUrl);
    }
}
