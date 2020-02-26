package eu.openg.guessnumberapi.gateway.implementation.PostgreSQL;

class QueryUtils {
    static final String TABLE_NAME = "game";
    static final String GAME_ID = "game_id";
    static final String GUESS_COUNT = "guess_count";
    static final String ACTUAL_NUMBER = "actual_number";

    static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            GAME_ID + "        serial PRIMARY KEY NOT NULL, " +
            GUESS_COUNT + "    int DEFAULT 0 NOT NULL, " +
            ACTUAL_NUMBER + "  int NOT NULL);";

    static final String INSERT_GAME_QUERY = "INSERT INTO " + TABLE_NAME + " (" + GUESS_COUNT + ","
            + ACTUAL_NUMBER + ") VALUES (?,?) RETURNING *;";

    static final String UPDATE_GUESSCOUNT_QUERY = "UPDATE " + TABLE_NAME + " SET " + GUESS_COUNT + " = "
            + GUESS_COUNT + " + 1 WHERE " + GAME_ID + " = ? RETURNING *;";

    static final String SELECT_GAME_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + GAME_ID + " = ?;";
}
