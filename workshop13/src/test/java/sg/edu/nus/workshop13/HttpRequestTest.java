package sg.edu.nus.workshop13;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    
    @LocalServerPort
}
