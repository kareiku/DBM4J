package io.github.kareiku;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface IDatabaseManager {
    void update(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException;

    @NotNull Stream<@NotNull Stream<?>> fetch(@NotNull String fmt, @NotNull Object @Nullable ... args) throws SQLException;
}
