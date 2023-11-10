package com.wisdomleaf.SpeakingClock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Speaking Clock",
		version = "1.0",
		description = "This project tells you the time in English words",
		contact = @Contact(
			name = "Abhishek Kumar",
			email = "abhishek.vskp@gmail.com"
		)
	) 
)
public class SpeakingClockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeakingClockApplication.class, args);
	}
}
