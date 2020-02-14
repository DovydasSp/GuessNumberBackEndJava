public class Main {
    public static void main(String[] args) {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        JSONSerializer serializer = new JacksonJSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }
}
