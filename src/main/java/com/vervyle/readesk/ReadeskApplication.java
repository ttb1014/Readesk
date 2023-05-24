package com.vervyle.readesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReadeskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ReadeskApplication.class, args);
    }
}
