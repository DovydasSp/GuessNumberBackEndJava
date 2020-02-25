package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.implementation.exception.PostgresqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PostgresqlGameRepo implements GameRepository {
    private static final Logger LOGGER = LogManager.getLogger(PostgresqlGameRepo.class);
    private final Connection connection;

    public PostgresqlGameRepo(PostgresqlConnection postgresqlConnection) {
        connection = postgresqlConnection.connectToPostgresqlDatabase();
        createGameTableIfNotExists();
    }

    private void createGameTableIfNotExists() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(QueryUtils.CREATE_TABLE_QUERY);
            LOGGER.info("PostgreSql database table created successfully");
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("CREATE TABLE failed. game table could not be created.", e);
        }
    }

    @Override
    public int saveNewGameAndReturnId(Game game) {
        try (PreparedStatement statement = connection.prepareStatement(QueryUtils.INSERT_GAME_QUERY)) {
            int id = insertAndReturnId(statement, game);
            LOGGER.info("PostgreSql new game insert successful.");
            return id;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("INSERT failed. New game could not be inserted.", e);
        }
    }

    private int insertAndReturnId(PreparedStatement statement, Game game) throws SQLException {
        statement.setInt(1, game.getGuessCount());
        statement.setInt(2, game.getActualNumber());
        return executeQueryAndReturnInt(statement);
    }

    @Override
    public int incrementAndReturnGuessCount(int gameId) {
        try (PreparedStatement statement = connection.prepareStatement(QueryUtils.UPDATE_GUESSCOUNT_QUERY)) {
            int updatedGuessCount = updateAndReturnGuessCount(statement, gameId);
            LOGGER.info("PostgreSql guessCount increment update successful.");
            return updatedGuessCount;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("UPDATE failed. guessCount could not be incremented", e);
        }
    }

    private int updateAndReturnGuessCount(PreparedStatement statement, int gameId) throws SQLException {
        statement.setInt(1, gameId);
        return executeQueryAndReturnInt(statement);
    }

    private int executeQueryAndReturnInt(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        }
        throw new PostgresqlException("Failed. Statement did not return any results.");
    }

    @Override
    public Game fetchGame(int gameId) {
        try (PreparedStatement statement = connection.prepareStatement(QueryUtils.SELECT_GAME_QUERY)) {
            statement.setInt(1, gameId);
            return selectAndReturnGameFromDB(statement);
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("SELECT failed. Game could not be fetched from database.", e);
        }
    }

    private Game selectAndReturnGameFromDB(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("gameid");
                int count = rs.getInt("guesscount");
                int number = rs.getInt("actualNumber");
                return new Game(id, count, number);
            }
            return null;
        }
    }

    private PostgresqlException logErrorAndReturnNewException(String message, Exception e) {
        LOGGER.error(message + "\n" + e);
        return new PostgresqlException(message);
    }
}
