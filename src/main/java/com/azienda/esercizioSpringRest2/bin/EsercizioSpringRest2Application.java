package com.azienda.esercizioSpringRest2.bin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.azienda.esercizioSpringRest2.controller","com.azienda.esercizioSpringRest2.service"})
@EntityScan(basePackages = {"com.azienda.esercizioSpringRest2.model"})
@EnableJpaRepositories(basePackages = {"com.azienda.esercizioSpringRest2.repository"})
public class EsercizioSpringRest2Application {

	public static void main(String[] args) {
		SpringApplication.run(EsercizioSpringRest2Application.class, args);

//test github
	}

}
