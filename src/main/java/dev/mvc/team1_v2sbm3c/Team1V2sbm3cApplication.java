package dev.mvc.team1_v2sbm3c;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc", "dev.jpa"})
@EnableJpaRepositories(basePackages = "dev.jpa")
@EntityScan(basePackages = "dev.jpa")
public class Team1V2sbm3cApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team1V2sbm3cApplication.class, args);
	}
	

}
