package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PostgresqlGameRepo implements GameRepository {
    private static final Logger LOGGER = LogManager.getLogger(PostgresqlGameRepo.class);
    private Connection con;

    public PostgresqlGameRepo() {
        connectToPostgresqlDatabase();
        createPostgresqlTable();
    }

    private void connectToPostgresqlDatabase() {
        con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "guessnumber");
            LOGGER.info("PostgreSql database opened successfully");
        } catch (Exception e) {
            LOGGER.error("Connection to PostgreSql database failed. " + e.getMessage());
        }
    }

    private void createPostgresqlTable() {
        try {
            Statement stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GAMEENTITY " +
                    "(GAMEID SERIAL PRIMARY KEY NOT NULL, GUESSCOUNT INT NOT NULL, GENERATEDNUMBER INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            LOGGER.info("PostgreSql database table created successfully");
        } catch (SQLException e) {
            LOGGER.error("Table creation to PostgreSql database failed. " + e.getMessage());
        }
    }

    @Override
    public int save(Game restGame) {
        int id = 0;
        try {
            id = insertAndReturnId(restGame);
            LOGGER.info("PostgreSql new game insert successful.");
        } catch (SQLException e) {
            LOGGER.error("PostgreSql new game insert failed. " + e.getMessage());
        }
        return id;
    }

    private int insertAndReturnId(Game restGame) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "insert into GAMEENTITY (GUESSCOUNT,GENERATEDNUMBER) VALUES (" + restGame.getGuessCount()
                + "," + restGame.getGeneratedNumber() + ");";
        stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        int id = 0;
        if (rs.next())
            id = rs.getInt(1);
        stmt.close();
        return id;
    }

    private void updateAndReturnId(int gameId, int guessCount) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "UPDATE GAMEENTITY SET GUESSCOUNT = " + guessCount + " WHERE GAMEID = " + gameId + ";";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    @Override
    public int incrementGuessCount(Game game) {
        int newGuessCount = game.getGuessCount() + 1;
        try {
            updateAndReturnId(game.getGameId(), newGuessCount);
            LOGGER.info("PostgreSql guessCount increment update successful.");
        } catch (SQLException e) {
            LOGGER.error("PostgreSql guessCount increment update failed. " + e.getMessage());
        }
        return newGuessCount;
    }

    @Override
    public Game fetchGameEntity(int gameId) {
        try {
            Game game = null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAMEENTITY WHERE GAMEID =" + gameId + ";");
            while (rs.next()) {
                int id = rs.getInt("gameid");
                int count = rs.getInt("guesscount");
                int number = rs.getInt("generatedNumber");
                game = new Game(id, count, number);
            }
            rs.close();
            stmt.close();
            return game;
        } catch (SQLException e) {
            LOGGER.error("PostgreSql select failed. " + e.getMessage());
        }
        return null;
    }
}
