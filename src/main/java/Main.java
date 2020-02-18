import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.nonNull;

public class Main {
    private static SparkController sparkController;

    public static void main(String[] args) {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        JSONSerializer serializer = createSerializer();
        sparkController = new SparkController(factory, serializer);
        sparkController.matchRoutes(4568);

        Runtime.getRuntime().addShutdownHook(new Thread(Main::stop));
    }

    private static void stop() {
        if (nonNull(sparkController))
            sparkController.stop();
    }

    private static JSONSerializer createSerializer() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new JacksonJSONSerializer(objectMapper);
    }
}
