package io.github.nprcz;


import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@SuppressWarnings("checkstyle:MissingJavadocType")
@SpringBootApplication
@EnableMongoRepositories
public class DmAppApplication {

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:FileTabCharacter"})
    public static void main(String[] args) {
        SpringApplication.run(DmAppApplication.class, args);
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
