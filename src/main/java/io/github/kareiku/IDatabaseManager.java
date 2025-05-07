package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * Provides an interface for interacting with a database manager.
 * <p>
 * Implementations of this interface should support executing update statements
 * (such as INSERT, UPDATE, DELETE) as well as retrieving data via SELECT queries.
 */
public interface IDatabaseManager {
    /**
     * Executes an update statement on the database.
     *
     * @param query a non-null SQL query string that modifies the database state;
     *              typically an INSERT, UPDATE, or DELETE statement
     * @throws SQLException if a database access error occurs or the SQL statement is invalid
     */
    void update(@NotNull String query) throws SQLException;

    /**
     * Executes a SELECT query and retrieves the results from the database.
     * The result is returned as a stream of rows, where each row is itself a stream of column values.
     *
     * @param query a non-null SQL SELECT statement
     * @return a non-null stream of rows, where each row is represented as a stream of column values
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    @NotNull Stream<Stream<?>> fetch(@NotNull String query) throws SQLException;
}
