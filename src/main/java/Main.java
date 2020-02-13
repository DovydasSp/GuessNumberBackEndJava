public class Main {
    public static void main(String[] args) {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        new SparkController(factory).matchRoutes();
    }
}
