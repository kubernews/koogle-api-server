package com.kubenews.koogleapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KoogleApiServerApplication {

    public static void main(String[] args) {
        KoogleNews a = new KoogleNews();
        SpringApplication.run(KoogleApiServerApplication.class, args);
    }

}
