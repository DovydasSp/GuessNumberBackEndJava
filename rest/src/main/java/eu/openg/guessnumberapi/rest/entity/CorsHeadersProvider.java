package eu.openg.guessnumberapi.rest.entity;

import java.util.HashMap;
import java.util.Map;

public class CorsHeadersProvider {
    public static final Map<String, String> CORS_HEADERS = createHeadersMap();

    private CorsHeadersProvider() {
    }

    private static Map<String, String> createHeadersMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT");
        headers.put("Access-Control-Allow-Headers", "Authorization,DNT,X-CustomHeader,Keep-Alive,User-Agent," +
                "X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range,request-body");
        headers.put("Access-Control-Expose-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With," +
                "If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range");
        return headers;
    }
}
