package com.kunyang.springcloud.controller;

import com.kunyang.springcloud.entities.CommonResult;
import com.kunyang.springcloud.entities.Payment;
import com.kunyang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author KunYang
 * @date 2021年06月30日 20:46
 */
@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    /**
     *读取到配置文件中配置的 server.port  ,给属性进行赋值
     */
    @Value("${server.port}")
    private String serverPort;


    /**
     *之前返回的JSON数据就是实体类,前后端分离专门指定一个类用来响应给前端
     */
    @PostMapping(value="/payment/creat")
    public CommonResult creat(@RequestBody Payment payment){

        int result = paymentService.create(payment);//调取业务层的方法,获得返回值大于0说明成功
        log.info("*****插入的结果:"+result);
        if(result >= 1){
            return new CommonResult(200,"插入数据成功,serverPort:"+serverPort,result);
        }else{
            return new CommonResult(404,"插入数据失败",null);
        }
    }
    /**对数据库执行读的操作
     * @Date: 2021/7/1
     * @Description
     */
    @GetMapping(value="/payment/get/{id}")
    public CommonResult<Payment> creat(@PathVariable("id") long id){

        Payment payment = paymentService.getPaymentById(id);//调取业务层的方法,获得返回值大于0说明成功
        log.info("*****插入的结果:"+payment);
        if(payment != null){
            return new CommonResult(200,"查询数据成功,serverPort:"+serverPort,payment);
        }else{
            return new CommonResult(404,"没有对应的记录,查询ID:"+id,null);
        }
    }

    /**手写一个对负载均衡的实现
     * @Date: 2021/7/6
     * @Description 手写负载均衡
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }

}



