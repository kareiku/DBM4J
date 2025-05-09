package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface IDatabaseManager {
    void update(@NotNull String query) throws SQLException;

    @NotNull Stream<Stream<?>> fetch(@NotNull String query) throws SQLException;
}
