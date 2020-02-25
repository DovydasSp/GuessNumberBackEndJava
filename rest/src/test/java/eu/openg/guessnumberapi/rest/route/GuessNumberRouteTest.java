package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGuessRequest;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.rest.exception.InvalidParamException;
import eu.openg.guessnumberapi.rest.exception.MissingParamException;
import eu.openg.guessnumberapi.rest.exception.ServerErrorException;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuessNumberRouteTest {
    private static GuessNumberRoute guessNumberRoute;
    @Mock
    private static UseCaseFactory useCaseFactory;
    @Mock
    private static JSONSerializer serializer;
    @Mock
    private static GuessNumberUseCase guessNumberUseCase;
    @Mock
    private static Request request;
    @Mock
    private static Response response;
    @Mock
    private static RestResponseConverter restResponseConverter;

    @BeforeEach
    void setUp() {
        guessNumberRoute = new GuessNumberRoute(useCaseFactory, serializer, restResponseConverter);
    }

    @Test
    void routeReceivesGameIdAndCallsCheckerWithIt() {
        int gameId = 37;
        int guessNumber = 5;

        createMocks(gameId, guessNumber);

        assertThrows(ServerErrorException.class, () -> guessNumberRoute.handle(request, response));
    }

    @Test
    void routeReceivesGameIdNullAndCallsCheckerWithIt() {
        assertThrows(MissingParamException.class, () -> guessNumberRoute.handle(request, response));
    }

    @Test
    void routeReceivesNullGuessAndCallsCheckerWithIt() {
        when(request.params("id")).thenReturn("1");
        assertThrows(InvalidParamException.class, () -> guessNumberRoute.handle(request, response));
    }

    private void createMocks(int gameId, int guessNumber) {
        RestGuessRequest guessRequest = mock(RestGuessRequest.class);
        when(guessRequest.getGuessNumber()).thenReturn(guessNumber);
        String body = "{\"guessNumber\":\"" + guessNumber + "\"}";

        when(useCaseFactory.buildGuessNumberUseCase()).thenReturn(guessNumberUseCase);
        when(request.params("id")).thenReturn(String.valueOf(gameId));
        when(request.body()).thenReturn(body);
        when(serializer.deserialize(body, RestGuessRequest.class)).thenReturn(Optional.of(guessRequest));
        when(serializer.serialize(any())).thenThrow(ServerErrorException.class);
        when(guessNumberUseCase.checkGuessAndReturnResponse(gameId, guessNumber))
                .thenReturn(new BoundaryGuessResponse(BoundaryGuessResultStatus.LESS, 1));
    }
}
