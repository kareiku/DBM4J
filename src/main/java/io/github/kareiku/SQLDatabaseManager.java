package io.github.kareiku;

import java.sql.*;

public class SQLDatabaseManager implements DatabaseManager {
    private final String url;

    public SQLDatabaseManager(String url) {
        this.url = url;
    }

    @Override
    public void create(String query, Object... args) {
        this.update(query, args);
    }

    @Override
    public String read(String query, Object... args) {
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                int columnCount = meta.getColumnCount();
                StringBuilder json = new StringBuilder();
                json.append('[');
                boolean firstRow = true;
                while (resultSet.next()) {
                    if (!firstRow) json.append(',');
                    json.append('{');
                    for (int i = 1; i <= columnCount; i++) {
                        json.append('"').append(meta.getColumnName(i)).append('"').append(':');
                        Object value = resultSet.getObject(i);
                        if (value == null) {
                            json.append("null");
                        } else if (value instanceof Number || value instanceof Boolean) {
                            json.append(value);
                        } else {
                            json.append('"').append(value.toString().replace("\"", "\\\"")).append('"');
                        }
                        if (i < columnCount) json.append(',');
                    }
                    json.append('}');
                    firstRow = false;
                }
                json.append(']');
                return json.toString();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "{}";
        }
    }

    @Override
    public void update(String query, Object... args) {
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(String query, Object... args) {
        this.update(query, args);
    }
}
