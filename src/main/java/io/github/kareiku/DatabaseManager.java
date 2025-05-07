package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * An abstract implementation of the {@link IDatabaseManager} interface.
 * <p>
 * This class provides a base implementation for interacting with a database, handling
 * the execution of update queries and the retrieval of data. It requires subclasses to
 * implement the method for obtaining a {@link Connection} to the database.
 */
public abstract class DatabaseManager implements IDatabaseManager {
    /**
     * Obtains a database connection. This method must be implemented by subclasses
     * to provide the appropriate {@link Connection} instance.
     *
     * @return a non-null {@link Connection} to the database
     * @throws SQLException if a database access error occurs while establishing the connection
     */
    protected abstract @NotNull Connection getConnection() throws SQLException;

    /**
     * Executes an update query (INSERT, UPDATE, DELETE) on the database.
     *
     * @param query a non-null SQL query string that modifies the database state;
     *              typically an INSERT, UPDATE, or DELETE statement
     * @throws SQLException if a database access error occurs or the SQL statement is invalid
     */
    @Override
    public void update(@NotNull String query) throws SQLException {
        try (
                Connection connection = this.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        }
    }


    /**
     * Executes a SELECT query and retrieves the results from the database.
     * The result is returned as a stream of rows, where each row is itself a stream of column values.
     *
     * @param query a non-null SQL SELECT statement
     * @return a non-null stream of rows, where each row is represented as a stream of column values
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    @Override
    public @NotNull Stream<Stream<?>> fetch(@NotNull String query) throws SQLException {
        List<List<?>> table = new ArrayList<>();
        try (
                Connection connection = this.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                List<Object> record = new ArrayList<>();
                table.add(record);
                for (int i = 1; i <= columnCount; i++) {
                    record.add(resultSet.getObject(i));
                }
            }
        }
        return table.stream().map(List::stream);
    }
}
