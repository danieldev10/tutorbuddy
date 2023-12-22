package ng.edu.mautech.tutorbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TutorbuddyApplication {

	public static void main(String[] args) {
		// This is the entry point of the application.
		// It's a standard Java main method.

		// SpringApplication.run is a method provided by Spring Boot that starts the
		// application.
		// It takes two arguments:
		// 1. The main application class (TutorbuddyApplication.class in this case).
		// 2. Command-line arguments (args).

		SpringApplication.run(TutorbuddyApplication.class, args);
	}
}
