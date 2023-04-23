package com.warzero.vinlandblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@MapperScan("com.warzero.vinlandblog.mapper")
@EnableScheduling
public class VinlandBlogBsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinlandBlogBsApplication.class, args);
    }

}
