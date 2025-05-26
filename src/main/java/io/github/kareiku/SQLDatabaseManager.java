package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SQLDatabaseManager implements DatabaseManager {
    private final String url;

    public SQLDatabaseManager(String url) {
        this.url = url;
    }

    @Override
    public void update(@NotNull String fmt, Object... args) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(fmt)
        ) {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }
            }
            statement.executeUpdate();
        }
    }

    @Override
    public Stream<Stream<?>> fetch(@NotNull String fmt, Object... args) throws SQLException {
        List<List<?>> table = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(fmt)
        ) {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    List<Object> record = new ArrayList<>();
                    table.add(record);
                    for (int i = 1; i <= columnCount; i++) {
                        record.add(resultSet.getObject(i));
                    }
                }
            }
        }
        return table.stream().map(List::stream);
    }
}
