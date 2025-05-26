package io.github.kareiku;

public interface DatabaseManager {
    void create(String query, Object... more);

    String read(String query, Object... more);

    void update(String query, Object... more);

    void delete(String query, Object... more);
}
