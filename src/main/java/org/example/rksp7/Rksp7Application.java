package org.example.rksp7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@EnableR2dbcRepositories
public class Rksp7Application {

    public static void main(String[] args) {
        SpringApplication.run(Rksp7Application.class, args);
    }

}
