package com.hero.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class HerodigitalApplication {

	public static void main(String[] args) {
		LOG.info("******* Starting HERO-DIGITAL Processor *******");
		SpringApplication.run(HerodigitalApplication.class, args);
	}

}
