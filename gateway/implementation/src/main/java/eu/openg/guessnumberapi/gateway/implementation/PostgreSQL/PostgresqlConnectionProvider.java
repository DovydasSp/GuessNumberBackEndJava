package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

import eu.openg.guessnumberapi.gateway.implementation.exception.PostgresqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.util.Objects.isNull;

public class PostgresqlConnectionProvider {
    private static final Logger LOGGER = LogManager.getLogger(PostgresqlConnectionProvider.class);
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public PostgresqlConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        String driverClass = "org.postgresql.Driver";
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw logErrorAndReturnNewException("Class " + driverClass + " was not found.", e);
        }
    }

    Connection getConnection() throws SQLException {
        if (isNull(connection) || connection.isClosed()) {
            openConnection();
        }
        return connection;
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            LOGGER.info("PostgreSql database opened successfully");
        } catch (Exception e) {
            throw logErrorAndReturnNewException("Connection failed. Could not connect to database.", e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("Connection close failed. Connection could not be closed.", e);
        }
    }

    private PostgresqlException logErrorAndReturnNewException(String message, Exception e) {
        LOGGER.error(message, e);
        return new PostgresqlException(message);
    }
}
