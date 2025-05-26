package io.github.kareiku;

<<<<<<< HEAD
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

=======
>>>>>>> ae2178c (Remade the SQL-based database manager to follow the CRUD interface)
import java.sql.*;

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
<<<<<<< HEAD
    public void update(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException {
        try (
                Connection connection = this.getConnection();
                PreparedStatement statement = connection.prepareStatement(fmt)
=======
    public void create(String query, Object... args) {
        this.update(query, args);
    }

    @Override
    public String read(String query, Object... args) {
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(query)
>>>>>>> ae2178c (Remade the SQL-based database manager to follow the CRUD interface)
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
<<<<<<< HEAD
    public @NotNull Stream<@NotNull Stream<?>> fetch(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException {
        List<List<?>> table = new ArrayList<>();
        try (
                Connection connection = this.getConnection();
                PreparedStatement statement = connection.prepareStatement(fmt)
=======
    public void update(String query, Object... args) {
        try (
                Connection connection = DriverManager.getConnection(this.url);
                PreparedStatement statement = connection.prepareStatement(query)
>>>>>>> ae2178c (Remade the SQL-based database manager to follow the CRUD interface)
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
