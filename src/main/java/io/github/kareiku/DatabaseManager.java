package io.github.kareiku;

<<<<<<< HEAD
import java.sql.SQLException;
import java.util.stream.Stream;

public interface IDatabaseManager {
    void update(String fmt, Object... args) throws SQLException;

    Stream<Stream<?>> fetch(String fmt, Object... args) throws SQLException;
=======
public interface DatabaseManager {
    void create(String query, Object... more);

    String read(String query, Object... more);

    void update(String query, Object... more);

    void delete(String query, Object... more);
>>>>>>> 1448cd1 (Created an interface to represent evety CRUD-based database manager)
}
