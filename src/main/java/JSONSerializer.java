import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.Optional;

public interface JSONSerializer {
    Optional<String> serialize(Object input);

    Map deserializeRequestBody(String body) throws JsonProcessingException;
}
