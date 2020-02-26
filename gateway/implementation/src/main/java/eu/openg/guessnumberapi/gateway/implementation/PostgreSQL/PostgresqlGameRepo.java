package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.implementation.exception.PostgresqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlGameRepo implements GameRepository {
    private static final Logger LOGGER = LogManager.getLogger(PostgresqlGameRepo.class);
    private final PostgresqlConnection postgresqlConnection;

    public PostgresqlGameRepo(PostgresqlConnection postgresqlConnection) {
        this.postgresqlConnection = postgresqlConnection;
        createGameTableIfNotExists();
    }

    private void createGameTableIfNotExists() {
        try (Statement statement = postgresqlConnection.getConnection().createStatement()) {
            statement.executeUpdate(QueryUtils.CREATE_TABLE_QUERY);
            LOGGER.info("PostgreSql database table {} created successfully", QueryUtils.GAME_TABLE_NAME);
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("CREATE TABLE failed. game table could not be created.", e);
        }
    }

    @Override
    public int saveNewGameAndReturnId(Game game) {
        try (PreparedStatement statement = prepareInsertQuery(game);
             ResultSet resultSet = statement.executeQuery()) {
            int id = extractFieldFromResultSet(resultSet, QueryUtils.GAME_ID, game.getGameId());
            LOGGER.info("PostgreSql new game with gameID: {} insert successful.", id);
            return id;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("INSERT failed. New game could not be inserted.", e);
        }
    }

    private PreparedStatement prepareInsertQuery(Game game) throws SQLException {
        PreparedStatement statement = postgresqlConnection.getConnection()
                .prepareStatement(QueryUtils.INSERT_GAME_QUERY);
        statement.setInt(1, game.getGuessCount());
        statement.setInt(2, game.getActualNumber());
        return statement;
    }

    private int extractFieldFromResultSet(ResultSet resultSet, String fieldToExtract, int gameId) throws SQLException {
        if (resultSet.next())
            return resultSet.getInt(fieldToExtract);
        throw new PostgresqlException("Failed. Statement did not return any results for game with id " + gameId);
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        try (PreparedStatement statement = prepareUpdateQuery(gameId);
             ResultSet resultSet = statement.executeQuery()) {
            int updatedGuessCount = extractFieldFromResultSet(resultSet, QueryUtils.GUESS_COUNT, gameId);
            LOGGER.info("PostgreSql guessCount increment to game with id {} update successful.", gameId);
            return updatedGuessCount;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("UPDATE failed. guessCount could not be incremented", e);
        }
    }

    private PreparedStatement prepareUpdateQuery(int gameId) throws SQLException {
        PreparedStatement statement = postgresqlConnection.getConnection()
                .prepareStatement(QueryUtils.UPDATE_GUESSCOUNT_QUERY);
        statement.setInt(1, gameId);
        return statement;
    }

    @Override
    public Game fetchGame(int gameId) {
        try (PreparedStatement statement = prepareSelectQuery(gameId);
             ResultSet resultSet = statement.executeQuery()) {
            Game returnedGame = returnGame(resultSet, gameId);
            LOGGER.info("PostgreSql game with id {} fetched successfully.", gameId);
            return returnedGame;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("SELECT failed. Game could not be fetched from database.", e);
        }
    }

    private PreparedStatement prepareSelectQuery(int gameId) throws SQLException {
        PreparedStatement statement = postgresqlConnection.getConnection()
                .prepareStatement(QueryUtils.SELECT_GAME_QUERY);
        statement.setInt(1, gameId);
        return statement;
    }

    private Game returnGame(ResultSet resultSet, int gameId) throws SQLException {
        if (resultSet.next()) {
            int id = resultSet.getInt(QueryUtils.GAME_ID);
            int count = resultSet.getInt(QueryUtils.GUESS_COUNT);
            int number = resultSet.getInt(QueryUtils.ACTUAL_NUMBER);
            return new Game(id, count, number);
        }
        throw new PostgresqlException("Failed. Statement did not return game data for game with id " + gameId);
    }

    private PostgresqlException logErrorAndReturnNewException(String message, Exception e) {
        LOGGER.error(message, e);
        return new PostgresqlException(message);
    }
}
