package org.lafabrique_epita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExpositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpositionApplication.class, args);
    }

}
