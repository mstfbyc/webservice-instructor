package com.urbanlegend;


import com.urbanlegend.user.User;
import com.urbanlegend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableSwagger2
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService) {
		return (args) -> {
			for (int i = 1; i < 20; i++) {
				User user = User.builder()
						.username("user"+i)
						.displayName("user"+i)
						.password("Password123")
						.build();
				userService.saveUser(user);
			}

		};
	}

	@Bean
	public Docket api(){
		SecurityReference securityReference = SecurityReference.builder()
				.reference("basicAuth")
				.scopes(new AuthorizationScope[0])
				.build();

		ArrayList<SecurityReference> reference = new ArrayList<>(1);
		reference.add(securityReference);

		ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
		securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

		ArrayList<SecurityScheme> auth = new ArrayList<>(1);
		auth.add(new BasicAuth("basicAuth"));
		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(auth)
				.securityContexts(securityContexts)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				;
	}



}
