package com.kunyang.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 在调用模块中对另一个服务的调用,使用Feign的时候,service是一个接口
 * @author KunYang
 * @date 2021年07月07日 21:18
 */
@Component
//调用这个服务名称下的服务提供者,如果发生了服务的降级调用这个类里面的方法
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService
{
    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")//http://localhost:8001/payment/hystrix/timeout/31
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
