package com.media.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = "com.media")
@PropertySource(value = { "classpath:hibernate.properties" })
public class TestConfiguration {

}
