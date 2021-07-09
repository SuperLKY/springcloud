package com.kunyang.springcloud.service;

import com.kunyang.springcloud.entities.CommonResult;
import com.kunyang.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author KunYang
 * @date 2021年07月06日 18:11
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")//你的微服务的名称叫什么,我的Feign就去找到这个服务,
public interface PaymentFeignService {

    /**
     * 使用到服务提供者的服务接口里面的方法
     * 在使用的时候,对方法更加的细化
     */
    @GetMapping(value = "/payment/get/{id}")//调用这个服务的这个地址 /payment/get31
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    /**
     *在这个服务的下面去找这个方法
     * @Date: 2021/7/7
     */
    @GetMapping(value = "/payment/feign/timeout")
     String paymentFeignTimeOut();
}
