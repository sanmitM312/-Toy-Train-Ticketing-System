package ticket.booking.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class LocalTimeDeserializer extends JsonDeserializer<Map<String, LocalTime>> {

    @Override
    public Map<String, LocalTime> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, String> rawMap = p.readValueAs(Map.class);
        Map<String, LocalTime> result = new HashMap<>();

        for (Map.Entry<String, String> entry : rawMap.entrySet()) {
            result.put(entry.getKey(), LocalTime.parse(entry.getValue())); // Convert String to LocalTime
        }

        return result;
    }
}
