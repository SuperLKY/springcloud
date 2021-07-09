package com.kunyang.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author KunYang
 * @date 2021年07月01日 11:20
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
 //手写一个负载均衡的算法,先把这个注解去掉
//    @LoadBalanced   //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
//applicationContext.xml <bean id="" class=""

