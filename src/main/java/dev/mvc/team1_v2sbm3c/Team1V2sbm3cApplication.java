package dev.mvc.team1_v2sbm3c;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import dev.mvc.patch.Patch;
import dev.mvc.patch.PatchRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc"})  
public class Team1V2sbm3cApplication implements CommandLineRunner {
  
  @Autowired
  private PatchRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(Team1V2sbm3cApplication.class, args);
	}
	
	@Override
  public void run(String... args) throws Exception {
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//	  repository.save(new Patch("시스템 접속자 증가", "시스템 장애 조치중", sdf.format(new Date()), 0));
	  
	}

}
