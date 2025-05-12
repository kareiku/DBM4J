package io.github.kareiku;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface IDatabaseManager {
    void setUrl(@NotNull String url);

    void setUrl(@NotNull String subprotocol, @NotNull String subname);

    void update(@NotNull String query) throws SQLException;

    @NotNull Stream<Stream<?>> fetch(@NotNull String query) throws SQLException;
}
