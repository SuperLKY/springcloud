
package com.kunyang.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 建一个类,专门为调用服务的时候做服务降级的处理,
 * 统一为接口里面的方法进行异常处理
 */
@Component //必须加 //必须加 //必须加
public class PaymentFallbackService implements PaymentHystrixService {


    @Override
    public String paymentInfo_OK(Integer id) {
        return "超时的异常,请稍后再试";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "超时的异常,请稍后再试";
    }
}
 
 
 
