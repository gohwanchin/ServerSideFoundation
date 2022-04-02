package sg.edu.nus.workshop13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.workshop13.controller.AddressbookController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Workshop13ApplicationTests {
	@Autowired
	private AddressbookController controller;

	@Test
	void contextLoads() {
		assertThat(controller.isNotNull());
	}

}
