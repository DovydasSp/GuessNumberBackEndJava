package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameRepo;
import eu.openg.guessnumberapi.gateway.fake.FakeNumberGateway;
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
        gameRepository = new FakeInMemoryGameRepo();
        createGameUseCase = new CreateGameInteractor(numberGateway, gameRepository);
    }

    @Test
    void createGameReturnsGameId1AndPutsNewGameToRepository() {
        int id = createGameUseCase.createGameAndReturnGameId();
        Game game = gameRepository.fetchGame(id);
        assertEquals(1, id);
        assertEquals(1, game.getGuessCount());
        assertEquals(2, game.getActualNumber());
    }
}
