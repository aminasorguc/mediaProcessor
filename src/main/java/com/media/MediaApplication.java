package com.media;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import ch.qos.logback.classic.Logger;

@SpringBootApplication(scanBasePackages = "com.media", exclude = HibernateJpaAutoConfiguration.class)
public class MediaApplication {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MediaApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting media application");
		SpringApplication.run(MediaApplication.class, args);
	}
}
