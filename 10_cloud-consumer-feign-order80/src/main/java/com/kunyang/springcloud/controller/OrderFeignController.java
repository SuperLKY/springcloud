package com.kunyang.springcloud.controller;

import com.kunyang.springcloud.entities.CommonResult;
import com.kunyang.springcloud.entities.Payment;
import com.kunyang.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KunYang
 * @date 2021年07月06日 18:28
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    /**
     * 通过Feign调用到服务提供者的方法
     * @Date: 2021/7/6
     * @Description
     */
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("id是"+id);
        return paymentFeignService.getPaymentById(id);  //实际执行的是服务提供者者服务调用地址

    }
    /**
     *访问这个地址,使用这个接口的实现类对,接口中方法的调用
     * 默认的OpenFeign访问的时间是1秒钟进行响应,如果执行的URL 超过了一秒钟就会显示超时的响应
     * @Date: 2021/7/7
     */
    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut()
    {
        return paymentFeignService.paymentFeignTimeOut();
    }


}
