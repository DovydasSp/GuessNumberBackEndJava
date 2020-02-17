import java.util.Optional;

public interface JSONSerializer {
    Optional<String> serialize(Object input);
}
