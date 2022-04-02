package sg.edu.nus.mockAssessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.mockAssessment.controller.CalculatorController;

@SpringBootTest
@AutoConfigureMockMvc
class MockAssessmentApplicationTests {
	private Random rand = new SecureRandom();

	@Autowired
	private CalculatorController calController;

	@Autowired
	private MockMvc mockMvc;

	// Looks up a controller and validate it's not empty
	@Test
	void contextLoads() {
		assertNotNull(calController);
	}

	@Test
	void shouldReturnCorrectResult() throws Exception {
		int oper1 = rand.nextInt(-50, 50);
		int oper2 = rand.nextInt(-50, 50);
		JsonObject payload = Json.createObjectBuilder()
				.add("oper1", oper1)
				.add("oper2", oper2)
				.add("ops", "plus")
				.build();

		RequestBuilder req = MockMvcRequestBuilders.post("/calculate")
				.header("User-Agent", "test")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.toString());
		MvcResult result = mockMvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();
		String respPayload = result.getResponse().getContentAsString();

		assertEquals(200, status);
		System.out.println(respPayload);
		Optional<JsonObject> opt = string2Json(respPayload);
		assertTrue(opt.isPresent());
		JsonObject obj = opt.get();
		for (String s : List.of("result", "timestamp", "userAgent"))
			assertFalse(obj.isNull(s));
		assertEquals(oper1 + oper2, obj.getInt("result"));
	}

	@Test
	void shouldFailWithBadOps() throws Exception {
		int oper1 = rand.nextInt(-50, 50);
		int oper2 = rand.nextInt(-50, 50);
		JsonObject payload = Json.createObjectBuilder()
				.add("oper1", oper1)
				.add("oper2", oper2)
				.add("ops", "abc")
				.build();

		RequestBuilder req = MockMvcRequestBuilders.post("/calculate")
				.header("User-Agent", "test")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload.toString());
		// Check that it throws an exception
		assertThrowsExactly(NestedServletException.class, () -> mockMvc.perform(req));
	}

	public static Optional<JsonObject> string2Json(String s) {
		try (InputStream is = new ByteArrayInputStream(s.getBytes())) {
			JsonReader r = Json.createReader(is);
			return Optional.of(r.readObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Optional.empty();
		}
	}
}
