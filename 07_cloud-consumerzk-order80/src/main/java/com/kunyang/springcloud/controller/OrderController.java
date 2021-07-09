package com.kunyang.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author KunYang
 * @date 2021年07月03日 16:24
 */
@RestController
@Slf4j
public class OrderController {

    //声明调用的地址   服务端暴露出来的地址
    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value="/consumer/payment/zk")
    public String paymentInfo(){


        String result = restTemplate.getForObject(INVOKE_URL+"/payment/zk", String.class);
        System.out.println(INVOKE_URL+"/payment/zk");//http://cloud-provider-payment/payment/zk
        System.out.println("消费者调用支付服务(zookeeper)--->result:" + result);
        return result;
    }
}
