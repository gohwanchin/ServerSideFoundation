package sg.edu.nus.workshop16;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.workshop16.repo.RedisRepo;

@SpringBootApplication
public class Workshop16Application {
	@Autowired
	RedisRepo redisRepo;

	public static void main(String[] args) {
		SpringApplication.run(Workshop16Application.class, args);
	}

	// @Override
	// public void run(String[] args) {
	// Map<Integer, String> map = new HashMap<>();
	// try (InputStream is = new FileInputStream(args[0])) {
	// JsonReader reader = Json.createReader(is);
	// JsonArray data = reader.readArray();

	// for (Object o : data) {
	// JsonObject game = (JsonObject) o;
	// int id = game.getInt("gid");
	// map.put(id, game.toString());
	// }

	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// System.out.println(map);
	// }
	@Bean
	CommandLineRunner runner() {
		return args -> {
			InputStream is = new FileInputStream("C:/Users/gohwa/Downloads/bgg/game.json");
			JsonReader reader = Json.createReader(is);
			JsonArray data = reader.readArray();

			// Maps/Casts JsonValue to JsonObject
			data.stream().map(v -> (JsonObject) v)
					.forEach((JsonObject v) -> {
						redisRepo.save(v);
					});
		};
	}
}
