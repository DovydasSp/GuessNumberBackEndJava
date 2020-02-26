package eu.openg.guessnumberapi.rest.route;

import spark.Request;
import spark.Response;
import spark.Route;

public class AcceptingOptionsRoute implements Route {
    @Override
    public Object handle(Request request, Response response) {
        response.status(200);
        return "";
    }
}
