package com.example.board3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Board3Application {

    public static void main(String[] args) {
        SpringApplication.run(Board3Application.class, args);
    }

}
