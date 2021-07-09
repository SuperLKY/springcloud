package com.kunyang.springcloud.controller;

import com.kunyang.springcloud.entities.CommonResult;
import com.kunyang.springcloud.entities.Payment;
import com.kunyang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * 读取到配置文件中配置的 server.port  ,给属性进行赋值
     */
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    /**
     * 8001端口对数据库读的操作
     * 之前返回的JSON数据就是实体类,前后端分离专门指定一个类用来响应给前端
     */
    @PostMapping(value = "/payment/creat")
    public CommonResult creat(@RequestBody Payment payment) {

        int result = paymentService.create(payment);//调取业务层的方法,获得返回值大于0说明成功
        log.info("*****插入的结果:" + result);
        if (result >= 1) {
            return new CommonResult(200, "插入数据成功,serverPort:" + serverPort, result);
        } else {
            return new CommonResult(404, "插入数据失败", null);
        }
    }

    /**
     * 8001端口对数据库写的操作
     *
     * @Date: 2021/7/2
     * @Description
     */
    @GetMapping(value = "/payment/get/{id}")//http://localhost:8001/payment/get/31
    public CommonResult<Payment> creat(@PathVariable("id") long id) {

        Payment payment = paymentService.getPaymentById(id);//调取业务层的方法,获得返回值大于0说明成功
        log.info("*****插入的结果:" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询数据成功,serverPort:" + serverPort, payment);
        } else {
            return new CommonResult(404, "没有对应的记录,查询ID:" + id, null);
        }
    }


    /**
     * 微服务的发现,通过DiscoveryClient获取服务注册中心的服务的相关的信息
     */
    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("=====element:" + element);

        }
        //通过服务的名字获得服务的实例,就支付模块的ip加端口号
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t"
                    + instance.getUri());
        }
        return this.discoveryClient;
    }

    /**
     * 手写一个对负载均衡的实现
     *
     * @Date: 2021/7/6
     * @Description 手写负载均衡
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    /**
     * 故意对服务的超时的控制,对方法的调用的时候,模拟这个方法需要的时间比较的长
     *
     * @Date: 2021/7/7
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut() {
        System.out.println("*****paymentFeignTimeOut from port: " + serverPort);
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}


