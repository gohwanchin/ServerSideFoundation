package sg.edu.nus.workshop17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sg.edu.nus.workshop17.service.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
class Workshop17ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WeatherService weatherSvc;

	@Test
	void contextLoads() {

	}

	@Test
	void shouldReturnWeatherOfSingapore() throws Exception {
		// Tests GET /weather?city=new+york
		RequestBuilder req = MockMvcRequestBuilders.get("/weather").queryParam("city", "new+york")
				.accept(MediaType.TEXT_HTML);
		MvcResult result = mockMvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();

		String payload = result.getResponse().getContentAsString();
		Assertions.assertEquals(200, status);
		Assertions.assertTrue(payload.indexOf("<span>New York</span>") > 0);
		System.out.println("payload" + payload);
	}

}
