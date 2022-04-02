package sg.edu.nus.workshop13;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static sg.edu.nus.workshop13.util.IOUtil.*;

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = Logger.getLogger(Workshop13Application.class.getName());

	public static void main(String[] args) {
		//SpringApplication.run(Workshop13Application.class, args);
		SpringApplication app = new SpringApplication(Workshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> optVals = appArgs.getOptionValues("dataDir");
		if(optVals == null){
			logger.log(Level.WARNING, "Error! Data directory not specified!");
		}
		else{
			createDir((String)optVals.get(0));
		}
		app.run(args);
	}

}
