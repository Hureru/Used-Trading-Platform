package com.hureru.design_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class DesignV2Application {

    public static void main(String[] args) {
        SpringApplication.run(DesignV2Application.class, args);
    }

}
