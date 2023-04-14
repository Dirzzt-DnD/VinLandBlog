package com.warzero.vinlandblog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${blog.access-img}")
    private String imgAccessPath;

    @Value("${blog.uploadImgPath}")
    private String imgUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("====================addResourceHandlers==========================");

        registry.addResourceHandler(imgAccessPath)
                .addResourceLocations("file:"+imgUploadPath);
        //swagger
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
