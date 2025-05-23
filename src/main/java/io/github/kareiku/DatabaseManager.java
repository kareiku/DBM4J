package io.github.kareiku;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseManager implements IDatabaseManager {
    private final String url;

    public DatabaseManager(@NotNull String url) {
        this.url = url;
    }

    public DatabaseManager(@NotNull String subprotocol, @NotNull String subname) {
        this.url = String.format("jdbc:%s:%s", subprotocol, subname);
    }

    private @NotNull Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url);
    }

    @Override
    public void update(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException {
        try (
                Connection connection = this.getConnection();
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
    public @NotNull Stream<@NotNull Stream<?>> fetch(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException {
        List<List<?>> table = new ArrayList<>();
        try (
                Connection connection = this.getConnection();
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
