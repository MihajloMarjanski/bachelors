package com.example.RA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@RestController
@EnableJpaRepositories("repo")
@EntityScan("model")
@ComponentScan({"controller"})
@ComponentScan({"service"})
@SpringBootApplication
public class RegistrationAuthority {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationAuthority.class, args);
		
	}
	//@Bean
	//public WebMvcConfigurer corsConfigurer() {
	//	return new WebMvcConfigurer() {
	//		@Override
	//		public void addCorsMappings(CorsRegistry registry) {
	//			registry.addMapping("/**").allowedOrigins("*");
	//		}
	//	};
	//}
	
}
