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
    private final PostgresqlConnectionProvider postgresqlConnectionProvider;

    public PostgresqlGameRepo(PostgresqlConnectionProvider postgresqlConnectionProvider) {
        this.postgresqlConnectionProvider = postgresqlConnectionProvider;
        createGameTableIfNotExists();
    }

    private void createGameTableIfNotExists() {
        try (Statement statement = postgresqlConnectionProvider.getConnection().createStatement()) {
            statement.executeUpdate(QueryUtils.CREATE_TABLE_QUERY);
            LOGGER.info("PostgreSql database table [{}] created successfully", QueryUtils.GAME_TABLE_NAME);
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("CREATE TABLE failed. game table could not be created.", e);
        }
    }

    @Override
    public int saveNewGameAndReturnId(Game game) {
        try (PreparedStatement statement = createQuery(QueryUtils.INSERT_GAME_QUERY, game.getGuessCount(),
                game.getActualNumber());
             ResultSet resultSet = statement.executeQuery()) {
            int id = extractFieldFromResultSet(resultSet, QueryUtils.GAME_ID, game.getGameId());
            LOGGER.info("PostgreSql new game with gameID: [{}] insert successful.", id);
            return id;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("INSERT failed. New game could not be inserted.", e);
        }
    }

    private PreparedStatement createQuery(String query, int... parameters) throws SQLException {
        PreparedStatement statement = postgresqlConnectionProvider.getConnection()
                .prepareStatement(query);
        int index = 1;
        for (int param : parameters) {
            statement.setInt(index++, param);
        }
        return statement;
    }

    private int extractFieldFromResultSet(ResultSet resultSet, String fieldToExtract, int gameId) throws SQLException {
        if (resultSet.next())
            return resultSet.getInt(fieldToExtract);
        throw new PostgresqlException("Failed. Statement did not return any results for game with gameId: " + gameId);
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        try (PreparedStatement statement = createQuery(QueryUtils.UPDATE_GUESS_COUNT_QUERY, gameId);
             ResultSet resultSet = statement.executeQuery()) {
            int updatedGuessCount = extractFieldFromResultSet(resultSet, QueryUtils.GUESS_COUNT, gameId);
            LOGGER.info("PostgreSql guessCount increment to game with gameId: [{}] update successful.", gameId);
            return updatedGuessCount;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("UPDATE failed. guessCount could not be incremented", e);
        }
    }

    @Override
    public Game fetchGame(int gameId) {
        try (PreparedStatement statement = createQuery(QueryUtils.SELECT_GAME_QUERY, gameId);
             ResultSet resultSet = statement.executeQuery()) {
            Game returnedGame = returnGame(resultSet, gameId);
            LOGGER.info("PostgreSql game with gameId: [{}] fetched successfully.", gameId);
            return returnedGame;
        } catch (SQLException e) {
            throw logErrorAndReturnNewException("SELECT failed. Game could not be fetched from database.", e);
        }
    }

    private Game returnGame(ResultSet resultSet, int gameId) throws SQLException {
        if (resultSet.next()) {
            int id = resultSet.getInt(QueryUtils.GAME_ID);
            int count = resultSet.getInt(QueryUtils.GUESS_COUNT);
            int number = resultSet.getInt(QueryUtils.ACTUAL_NUMBER);
            return new Game(id, count, number);
        }
        throw new PostgresqlException("Failed. Statement did not return game data for game with gameId: " + gameId);
    }

    private PostgresqlException logErrorAndReturnNewException(String message, Exception e) {
        LOGGER.error(message, e);
        return new PostgresqlException(message);
    }
}
