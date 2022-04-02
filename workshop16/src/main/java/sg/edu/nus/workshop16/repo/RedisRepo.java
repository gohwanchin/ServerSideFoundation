package sg.edu.nus.workshop16.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import sg.edu.nus.workshop16.model.Game;

@Repository
public class RedisRepo {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(JsonObject obj) {
        int gid = obj.getInt("gid");
        redisTemplate.opsForHash().put(
                "%d".formatted(gid),
                "rec",
                obj.toString());
    }

    public Set<String> findKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Optional<Game> get(String gid) {
        String rec = (String) redisTemplate.opsForHash().get(gid, "rec");
        if (rec != null)
            return Optional.of(Game.create(rec));
        return Optional.empty();
    }
}
