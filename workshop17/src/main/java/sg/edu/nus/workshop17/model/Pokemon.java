package sg.edu.nus.workshop17.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Pokemon {
    private static String[] IMAGES = {
            "sprites", "versions", "generation-i", "red-blue"
    };

    private String name;
    private List<String> photos = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public static Pokemon create(String json) throws IOException {
        Pokemon p = new Pokemon();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            p.setName(o.getString("name"));
            for (String i : IMAGES)
                o = o.getJsonObject(i);
            List<String> l = o.values().stream()
                    .filter(v -> v != null)
                    .map(v -> v.toString().replaceAll("\"", ""))
                    .toList();
            p.setPhotos(l);
        }
        return p;
    }
}
