package sg.edu.nus.workshop16.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.workshop16.model.Game;
import sg.edu.nus.workshop16.repo.RedisRepo;
import sg.edu.nus.workshop16.service.GameService;

@RestController
@RequestMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameRestController {
    @Autowired
    private GameService gameSvc;

    @Autowired
    private RedisRepo redisRepo;

    @GetMapping("/{gid}")
    public ResponseEntity<String> getGame(@PathVariable String gid) {
        Optional<Game> game = redisRepo.get(gid);
        if (game.isEmpty()) {
            JsonObject result = Json.createObjectBuilder()
                    .add("error", "gid = %s not found".formatted(gid))
                    .build();
            return ResponseEntity.status(404).body(result.toString());
        }
        return ResponseEntity.ok(game.get().toJson().toString());
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchGames(@RequestParam String pattern) {

        Set<String> keySet = gameSvc.searchKeys("*" + pattern + "*");
        JsonArrayBuilder ab = Json.createArrayBuilder();
        // TODO show game name in the URL
        keySet.stream().map((String gid) -> {
            return "/game/%s".formatted(gid);
        }).forEach((String url) -> {
            ab.add(url);
        });

        JsonArray gameArray = ab.build();
        return ResponseEntity.ok(gameArray.toString());
    }

}
