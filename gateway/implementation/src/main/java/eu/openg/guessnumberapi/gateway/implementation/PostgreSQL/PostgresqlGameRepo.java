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
        connection = postgresqlConnection.openConnection();
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
            int id = executeInsertQuery(statement, game);
            LOGGER.info("PostgreSql new game insert successful.");
            return id;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("INSERT failed. New game could not be inserted.", e);
        }
    }

    private int executeInsertQuery(PreparedStatement statement, Game game) throws SQLException {
        statement.setInt(1, game.getGuessCount());
        statement.setInt(2, game.getActualNumber());
        try (ResultSet resultSet = statement.executeQuery()) {
            return returnField(resultSet);
        }
    }

    private int returnField(ResultSet resultSet) throws SQLException {
        if (resultSet.next())
            return resultSet.getInt(1);
        throw new PostgresqlException("Failed. Statement did not return any results.");
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        try (PreparedStatement statement = connection.prepareStatement(QueryUtils.UPDATE_GUESSCOUNT_QUERY)) {
            int updatedGuessCount = executeUpdateQuery(statement, gameId);
            LOGGER.info("PostgreSql guessCount increment update successful.");
            return updatedGuessCount;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("UPDATE failed. guessCount could not be incremented", e);
        }
    }

    private int executeUpdateQuery(PreparedStatement statement, int gameId) throws SQLException {
        statement.setInt(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return returnField(resultSet);
        }
    }

    @Override
    public Game fetchGame(int gameId) {
        try (PreparedStatement statement = connection.prepareStatement(QueryUtils.SELECT_GAME_QUERY)) {
            Game returnedGame = executeSelectQuery(statement, gameId);
            LOGGER.info("PostgreSql game fetch successful.");
            return returnedGame;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("SELECT failed. Game could not be fetched from database.", e);
        }
    }

    private Game executeSelectQuery(PreparedStatement statement, int gameId) throws SQLException {
        statement.setInt(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return returnGame(resultSet);
        }
    }

    private Game returnGame(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            int id = resultSet.getInt(QueryUtils.GAME_ID);
            int count = resultSet.getInt(QueryUtils.GUESS_COUNT);
            int number = resultSet.getInt(QueryUtils.ACTUAL_NUMBER);
            return new Game(id, count, number);
        }
        throw new PostgresqlException("Failed. Statement did not return any results.");
    }

    private PostgresqlException logErrorAndReturnNewException(String message, Exception e) {
        LOGGER.error(message, e);
        return new PostgresqlException(message);
    }
}
