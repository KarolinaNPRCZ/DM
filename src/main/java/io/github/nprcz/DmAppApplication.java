package io.github.nprcz;


import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;




@SpringBootApplication
public class DmAppApplication {


	public static void main(String[] args)  {
		SpringApplication.run(DmAppApplication.class, args);
	}
    @Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}
