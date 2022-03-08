package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {
        return args -> {
            repository.save(new Person("Petr", "Petr XXXX", 26));
            repository.save(new Person("Helena", "Helena AAAAA", 25));
            repository.save(new Person("Tomáš", "Tomáš ZZZZ", 27));
        };
    }

}
