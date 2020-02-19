package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.exception.RestErrorResponseEntity;
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
            mapErrorResponse(response, restException.getStatus(), restException.getErrorResponseEntity());
        } else {
            mapErrorResponse(response, 500, new RestErrorResponseEntity(exception.getMessage()));
        }
    }

    private void mapErrorResponse(Response response, int statusCode, RestErrorResponseEntity responseEntity) {
        response.status(statusCode);
        serializer.serialize(responseEntity).ifPresent(response::body);
    }
}
