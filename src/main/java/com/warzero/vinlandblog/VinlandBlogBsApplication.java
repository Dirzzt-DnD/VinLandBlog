package com.warzero.vinlandblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.warzero.vinlandblog.mapper")
public class VinlandBlogBsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinlandBlogBsApplication.class, args);
    }

}
