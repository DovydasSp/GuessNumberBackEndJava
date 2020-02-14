public class Main {
    public static void main(String[] args) {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        GameEntitySerializer serializer = new JSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }
}
