package com.semi.lynk.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "com.semi.lynk")

@MapperScan(basePackages = "com.semi.lynk", annotationClass = Mapper.class)
public class ContextConfig {

    // 메시지 사용 설정용
    @Bean // message.properties 파일 자바 객체 형식으로 읽기
    public ReloadableResourceBundleMessageSource messageSource (){

        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

        source.setBasename("classpath:/messages/human/human");
        source.setDefaultEncoding("UTF-8");

        Locale.setDefault(Locale.KOREA); // 한국어 메시지 설정
        return source;
    }

//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource (){
//
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//
//        source.setBasename("classpath:/messages/message");
//        source.setDefaultEncoding("UTF-8");
//
//        Locale.setDefault(Locale.KOREA); // 한국어 메시지 설정
//        return source;
//    }

}
