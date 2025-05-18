package com.example.cars;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(allowCredentials = "*")
public class BridgeApplication {

	public static void main(String[] args) {
		// Load environment variables
		Dotenv dotenv = Dotenv.load();

		// Example: access a variable
		String sid = dotenv.get("TWILIO_ACCOUNT_SID");
		System.out.println("TWILIO SID: " + sid); // Optional debug line

		SpringApplication.run(BridgeApplication.class, args);
	}
}