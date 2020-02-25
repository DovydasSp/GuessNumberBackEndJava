package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

import eu.openg.guessnumberapi.gateway.implementation.exception.PostgresqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnection {
    private static final Logger LOGGER = LogManager.getLogger(PostgresqlConnection.class);
    private Connection connection;

    Connection connectToPostgresqlDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "guessnumber");
            LOGGER.info("PostgreSql database opened successfully");
            return connection;
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
        LOGGER.error(message + "\n" + e);
        return new PostgresqlException(message);
    }
}