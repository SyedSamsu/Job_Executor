package com.project.jobexecutor;

import com.project.jobexecutor.model.User;
import com.project.jobexecutor.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableScheduling
public class JobexecutorApplication {

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(JobexecutorApplication.class, args);
	}

	@PostConstruct
	protected void init() {
		User admin = new User();
		admin.setName("admin");
		admin.setPassword("admin");
		admin.setRole("admin");
		User demoUser = new User();
		demoUser.setName("user");
		demoUser.setPassword("user");
		demoUser.setRole("user");
		userRepo.save(admin);
		userRepo.save(demoUser);
	}


	@Bean
	public Formatter<LocalDateTime> localDateTimeFormatter() {
		return new Formatter<LocalDateTime>() {
			@Override
			public LocalDateTime parse(String text, java.util.Locale locale) {
				return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
			}

			@Override
			public String print(LocalDateTime object, java.util.Locale locale) {
				return object.toString();
			}
		};
	}
}
