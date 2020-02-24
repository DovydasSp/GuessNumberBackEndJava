package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameEntityRepo;
import eu.openg.guessnumberapi.gateway.fake.FakeNumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.implementation.CreateGameInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateGameIntegrationTest {
    private CreateGameUseCase createGameUseCase;
    private GameEntityRepository gameEntityRepository;

    @BeforeEach
    void setUp() {
        NumberGateway numberGateway = new FakeNumberGateway();
        GameIdProvider gameIdProvider = new FakeGameIdProvider();
        gameEntityRepository = new FakeInMemoryGameEntityRepo(gameIdProvider);
        createGameUseCase = new CreateGameInteractor(numberGateway, gameEntityRepository);
    }

    @Test
    void createGameReturnsGameId10AndPutsNewGameEntityToRepository() {
        int id = createGameUseCase.createGameAndReturnGameId();
        GameEntity gameEntity = gameEntityRepository.fetchGameEntity(id);
        assertEquals(10, id);
        assertEquals(3, gameEntity.getGeneratedNumber());
        assertEquals(1, gameEntity.getGuessCount());
    }
}
