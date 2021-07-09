package com.kunyang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author KunYang
 * @date 2021年07月01日 18:37
 * 这是一个服务的注册中心并不用去写业务的逻辑
 * 注册的中心管理进来注册的服务者
 */
@SpringBootApplication
@EnableEurekaServer     //表示这是一个服务的注册中心
public class EurekaMain7001 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
