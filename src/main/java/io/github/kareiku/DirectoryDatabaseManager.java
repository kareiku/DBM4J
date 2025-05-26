package io.github.kareiku;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class DirectoryDatabaseManager implements DatabaseManager {
    private final Path root;

    public DirectoryDatabaseManager(Path root) {
        this.root = root.toAbsolutePath().normalize();
        this.create("");
    }

    @Override
    public void create(String relativePath, Object... ignored) {
        Path dir = this.root.resolve(relativePath);
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public String read(String relativePath, Object... ignored) {
        Path dir = this.root.resolve(relativePath);
        return Files.isDirectory(dir) ? this.buildJson(dir, 0) : "{}";
    }

    @Override
    public void update(String unsupported, Object... ignored) {
        System.err.println("Unsupported operation. Please use create or delete.");
    }

    @Override
    public void delete(String relativePath, Object... ignored) {
        Path dir = this.root.resolve(relativePath);
        if (Files.isDirectory(dir)) {
            try (Stream<Path> walk = Files.walk(dir).sorted(Comparator.reverseOrder())) {
                Iterable<Path> paths = walk::iterator;
                for (Path path : paths) {
                    Files.deleteIfExists(path);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private String buildJson(Path path, int depth) {
        StringBuilder json = new StringBuilder();
        json.append('{').append("\"name\":\"").append(path.getFileName()).append('"').append(',');
        if (Files.isDirectory(path)) {
            json.append("\"dirs\":[");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                boolean first = true;
                for (Path item : stream) {
                    if (!first) json.append(',');
                    json.append(this.buildJson(item, depth + 2));
                    first = false;
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            json.append(']');
        }
        json.append('}');
        return json.toString();
    }
}
