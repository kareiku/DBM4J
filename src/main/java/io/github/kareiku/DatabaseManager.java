package io.github.kareiku;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface IDatabaseManager {
    void update(String fmt, Object... args) throws SQLException;

    Stream<Stream<?>> fetch(String fmt, Object... args) throws SQLException;
}
