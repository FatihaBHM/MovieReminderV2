package org.lafabrique_epita;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class ExpositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpositionApplication.class, args);
    }

}
