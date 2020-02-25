package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

class QueryUtils {
    static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS GAME " +
            "(GAMEID        SERIAL PRIMARY KEY NOT NULL, " +
            "GUESSCOUNT     INT DEFAULT 0 NOT NULL, " +
            "ACTUALNUMBER   INT DEFAULT 0 NOT NULL);";

    static final String INSERT_GAME_QUERY = "INSERT INTO GAME (GUESSCOUNT,ACTUALNUMBER) VALUES (?,?) RETURNING GAMEID;";

    static final String UPDATE_GUESSCOUNT_QUERY = "UPDATE GAME SET GUESSCOUNT = GUESSCOUNT + 1 " +
            "WHERE GAMEID = ? RETURNING GUESSCOUNT;";

    static final String SELECT_GAME_QUERY = "SELECT * FROM GAME WHERE GAMEID = ?;";

    static final String GAME_ID = "GAMEID";
    static final String GUESS_COUNT = "GUESSCOUNT";
    static final String ACTUAL_NUMBER = "ACTUALNUMBER";
}
