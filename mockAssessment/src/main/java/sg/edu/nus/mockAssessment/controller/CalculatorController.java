package sg.edu.nus.mockAssessment.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatorController {
    @PostMapping
    public ResponseEntity<String> postCalculate(@RequestBody String payload,
            @RequestHeader("User-Agent") String userAgent)  {
        JsonObject body;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader reader = Json.createReader(is);
            body = reader.readObject();
        } catch (Exception e) {
            body = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return ResponseEntity.internalServerError().body(body.toString());
        }
        int x = body.getInt("oper1");
        int y = body.getInt("oper2");
        int sum = 0;
        switch (body.getString("ops")) {
            case "plus":
                sum = x + y;
                break;
            case "minus":
                sum = x - y;
                break;
            case "multiply":
                sum = x * y;
                break;
            case "divide":
                sum = x / y;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator %s\n".formatted(body.getString("ops")));
                // JsonObject result = Json.createObjectBuilder()
                //         .add("error", "Invalid operator: %s".formatted(body.getString("ops"))).build();
                // return ResponseEntity.badRequest().body(result.toString());
        }
        JsonObject resp = Json.createObjectBuilder().add("result", sum)
                .add("timestamp", Long.toString(System.currentTimeMillis()))
                .add("userAgent", userAgent).build();
        return ResponseEntity.ok(resp.toString());
    }
}
