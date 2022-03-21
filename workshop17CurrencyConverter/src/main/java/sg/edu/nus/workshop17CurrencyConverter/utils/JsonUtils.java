package sg.edu.nus.workshop17CurrencyConverter.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class JsonUtils {
    public static JsonObject getJsonObject(String json) throws IOException {
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readObject();
        }
    }
}
