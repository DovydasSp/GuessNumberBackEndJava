import game.GameEntity;
import game.RestGameEntity;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostGameRoute implements Route {
    private final UseCaseFactory useCaseFactory;
    private final GameEntitySerializer serializer;

    public PostGameRoute(UseCaseFactory useCaseFactory, GameEntitySerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) {
        GenerateNumberUseCase interactor = useCaseFactory.buildGenerateNumberInteractor();
        GameEntity gameEntity = interactor.execute();
        response.body(serializer.serialize(convertGameEntity(gameEntity)));
        return response.body();
    }

    private RestGameEntity convertGameEntity(GameEntity gameEntity) {
        return new RestGameEntity(gameEntity.returnGameId(),
                gameEntity.returnGuessCount(), gameEntity.returnGeneratedNumber());
    }
}
