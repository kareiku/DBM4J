package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseManager implements IDatabaseManager {
    private String url;

    public DatabaseManager() {
        this.url = null;
    }

    @Override
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    @Override
    public void setUrl(@NotNull String subprotocol, @NotNull String subname) {
        this.url = String.format("jdbc:%s:%s", subprotocol, subname);
    }

    private @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url);
    }

    @Override
    public void update(@NotNull String query) throws SQLException {
        try (
                Connection connection = this.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        }
    }

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
