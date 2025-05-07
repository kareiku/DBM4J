package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * A concrete implementation of {@link DatabaseManager} for interacting with an SQLite database.
 * <p>
 * This class connects to an SQLite database using the database file located within the classpath.
 */
public class SQLiteDatabaseManager extends DatabaseManager {
    private static final String JDBC_SQLITE_URL_PREFIX = "jdbc:sqlite:";
    private final String sqliteDatabaseName;

    /**
     * Constructs a new {@link SQLiteDatabaseManager} instance.
     *
     * @param sqliteDatabaseName the name of the SQLite database file
     */
    public SQLiteDatabaseManager(String sqliteDatabaseName) {
        this.sqliteDatabaseName = sqliteDatabaseName;
    }

    /**
     * Establishes a connection to the SQLite database by obtaining the database file
     * from the current classloader's resource path.
     *
     * @return a non-null {@link Connection} to the SQLite database
     * @throws SQLException if a database access error occurs while establishing the connection
     */
    @Override
    protected @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_SQLITE_URL_PREFIX + Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(this.sqliteDatabaseName)).getPath());
    }
}
