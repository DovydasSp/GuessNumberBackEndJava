package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.exception.RestErrorResponse;
import eu.openg.guessnumberapi.rest.exception.RestException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class SparkExceptionHandler implements ExceptionHandler<RuntimeException> {
    private final JSONSerializer serializer;

    SparkExceptionHandler(JSONSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void handle(RuntimeException exception, Request request, Response response) {
        if (exception instanceof RestException) {
            final RestException restException = (RestException) exception;
            mapErrorResponse(response, restException.getStatus(), restException.getErrorResponse());
        } else {
            mapErrorResponse(response, 500, new RestErrorResponse(exception.getMessage()));
        }
    }

    private void mapErrorResponse(Response response, int statusCode, RestErrorResponse responseEntity) {
        response.status(statusCode);
        serializer.serialize(responseEntity).ifPresent(response::body);
    }
}
