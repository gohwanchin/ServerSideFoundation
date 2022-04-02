package sg.edu.nus.iss.workshop11;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Workshop11Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop11Application.class);
	public static void main(String[] args) {
		logger.info("Workshop 11");
		logger.debug("Workshop 11 -D");
		SpringApplication app = new SpringApplication(Workshop11Application.class);
		String portNum = "3000";

		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> optVals = appArgs.getOptionValues("port");
		logger.info("optVals > "+ optVals);
		if (optVals == null || optVals.get(0) == null){
			portNum = System.getenv("PORT");
		}else{
			portNum = (String)optVals.get(0);
		}

		if (portNum!=null){
			app.setDefaultProperties(Collections.singletonMap("server.port",portNum));
		}
		
		//SpringApplication.run(Workshop11Application.class, args);
		
		app.run(args);
	}

}
