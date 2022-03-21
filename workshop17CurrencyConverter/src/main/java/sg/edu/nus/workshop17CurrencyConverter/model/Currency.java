package sg.edu.nus.workshop17CurrencyConverter.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Currency {
    private String name;
    private String symbol;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Currency create(JsonObject o){
        Currency c = new Currency();
        c.setName(o.getString("currencyName"));
        c.setId(o.getString("id"));
        if (o.containsKey("currencySymbol"))
            c.setSymbol(o.getString("currencySymbol"));
        else
            c.setSymbol(" ");
        return c;
    }

    public static List<Currency> createList(String json) throws IOException{
        List<Currency> list = new ArrayList<>();
        try (InputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"))) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();

            o = o.getJsonObject("results");
            for(String i : o.keySet()){
                JsonObject c = o.getJsonObject(i);
                Currency curr = Currency.create(c);
                list.add(curr);
            }
        }
        return list;
    }
}
