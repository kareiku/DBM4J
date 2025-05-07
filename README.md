# DBM4J

A lightweight set of Java classes and interfaces for easy database access. Designed for simple queries, yet fully extensible.

## Table of Contents

- **IDatabaseManager**: Interface of the database manager. Contains method signatures for fetching and updating, based on queries.
- **DatabaseManager**: Abstract class that define how queries will return data and update tables.
- **MySQLDatabaseManager**: Concrete database manager for MySQL connections.
- **SQLiteDatabaseManager**: Concrete database manager for SQLite connections.

## Usage

Precompiled versions of the library can be found under the `releases/` directory. This is done to allow ease of download without requiring GitHub's GUI.

## Build It Yourself

1. Clone the repository.
2. Compile with Maven (`mvn compile package`).

## Features

- Modular components, reusable across many projects
- Lightweight and extensible design philosophy

## Contributing

Have a suggestion? Whether it's about structure, safety, or improvements to the code, feel free to open a pull request or create an issue with the appropriate label.

## License

Distributed under the MIT License. See `LICENSE.txt` for details.

## Contact

Kare - xaxvu7@gmail.com

Project Link: https://github.com/kareiku/DBM4J
