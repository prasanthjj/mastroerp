package com.erp.mastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MastroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MastroApplication.class, args);
	}

}
