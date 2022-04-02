package sg.edu.nus.workshop16;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.nus.workshop16.controller.QuoteController;
import sg.edu.nus.workshop16.service.QuoteService;

@SpringBootTest
class Workshop16ApplicationTests {
	@Autowired
	private QuoteService quoteSvc;

	@Autowired
	QuoteController quoteController;

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(quoteSvc);
	}

	@Test
	void testQuoteController() {
		Assertions.assertNotNull(quoteController);

	}

}
