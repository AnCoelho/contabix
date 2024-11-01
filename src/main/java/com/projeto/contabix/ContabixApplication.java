package com.projeto.contabix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.projeto.contabix")
@EnableJpaRepositories(basePackages = "com.projeto.contabix.repository")
public class ContabixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContabixApplication.class, args);
	}

}
