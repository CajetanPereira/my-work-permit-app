package com.workpermit.permit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PermitServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermitServiceApplication.class, args);
    }
}
