import java.util.Optional;

public interface JSONSerializer {
    Optional<String> serialize(Object input);

    <T> Optional<T> deserialize(String body, Class<T> aClass);
}
