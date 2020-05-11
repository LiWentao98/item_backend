package com.item_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(value = "com.item_backend.mapper")
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.item_backend"})
public class Application {
    //testGit AAA
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
