package sg.edu.nus.mockAssessment2.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Deck {
    private String id;
    private Boolean shuffled;
    private int remaining;
    private List<Card> cards = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getShuffled() {
        return shuffled;
    }

    public void setShuffled(Boolean shuffled) {
        this.shuffled = shuffled;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public static Deck create(String json) throws IOException {
        Deck d = new Deck();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();

            d.setId(o.getString("deck_id"));
            d.setShuffled(true);
            d.setRemaining(o.getInt("remaining"));

            if (o.containsKey("cards")) {
                JsonArray cards = o.getJsonArray("cards");
                List<Card> l = cards.stream()
                        .map(c -> (JsonObject) c)
                        .map(c -> Card.create(c))
                        .toList();
                d.setCards(l);
            }
        }
        return d;
    }
}
