import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public interface JSONSerializer {
    Optional<String> serialize(Object input);

    ObjectMapper fetchObjectMapper();
}
