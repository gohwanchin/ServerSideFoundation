package sg.edu.nus.workshop16.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.workshop16.service.QuoteService;

@RestController
@RequestMapping(path = "/quote", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuoteController {

    @Autowired
    QuoteService quoteSvc;

    @GetMapping
    public ResponseEntity<String> getQuote(@RequestParam(defaultValue = "1") String count) {
        int countInt = 0;
        String q;
        JsonArrayBuilder ab = Json.createArrayBuilder();

        try {
            countInt = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        for (int i = 0; i < countInt; i++) {
            q = quoteSvc.getQuote();
            ab.add(q);
        }
        JsonObject result = Json.createObjectBuilder().add("quotes", ab)
                .add("timestamp", System.currentTimeMillis())
                .build();
        return ResponseEntity.ok(result.toString());
    }
}
