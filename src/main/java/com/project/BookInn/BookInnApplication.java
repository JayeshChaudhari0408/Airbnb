package com.project.BookInn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookInnApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInnApplication.class, args);
	}

}
