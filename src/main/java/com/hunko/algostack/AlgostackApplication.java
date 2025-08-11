package com.hunko.algostack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AlgostackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgostackApplication.class, args);
    }

}
