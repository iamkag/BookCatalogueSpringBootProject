package com.crud.pkagioglou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookAppApplication.class, args);
        System.out.println("Application Is ON");
    }
}
