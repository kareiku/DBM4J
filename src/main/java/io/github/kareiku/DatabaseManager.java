package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public abstract class DatabaseManager implements IDatabaseManager {
    protected abstract @NotNull Connection getConnection() throws SQLException;

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
