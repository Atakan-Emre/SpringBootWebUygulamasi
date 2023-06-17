package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.services.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	public CommandLineRunner createInitialUsers(UserServiceImpl userServiceImpl) {
		return (args) -> {
			User user = new User();
			user.setUsername("user1");
			user.setDisplayName("display1");
			user.setPassword("P4ssword");
			userServiceImpl.save(user);
		};
	}
}
