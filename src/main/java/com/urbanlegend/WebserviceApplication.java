package com.urbanlegend;


import com.urbanlegend.user.User;
import com.urbanlegend.user.UserRepository;
import com.urbanlegend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService){
		return ( args) ->  {
			User user = User.builder()
					.username("User1")
					.displayName("User1")
					.password("Password123")
					.build();
			userService.saveUser(user);
		};
	}
}
