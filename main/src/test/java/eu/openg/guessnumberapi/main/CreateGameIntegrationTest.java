package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeNumberGateway;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.implementation.CreateGameInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateGameIntegrationTest {
    private CreateGameUseCase createGameUseCase;
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        NumberGateway numberGateway = new FakeNumberGateway();
        GameIdProvider gameIdProvider = new FakeGameIdProvider();
        gameRepository = new InMemoryGameRepo(gameIdProvider);
        createGameUseCase = new CreateGameInteractor(numberGateway, gameRepository);
    }

    @Test
    void createGameReturnsGameId10AndPutsNewGameToRepository() {
        int id = createGameUseCase.createGameAndReturnGameId();
        Game game = gameRepository.fetchGame(id);
        assertEquals(10, id);
        assertEquals(3, game.getActualNumber());
        assertEquals(0, game.getGuessCount());
    }
}
