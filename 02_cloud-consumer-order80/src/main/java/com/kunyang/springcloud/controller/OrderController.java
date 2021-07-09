package com.kunyang.springcloud.controller;


import com.kunyang.springcloud.entities.CommonResult;
import com.kunyang.springcloud.entities.Payment;
import com.kunyang.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author KunYang
 * @date 2021年07月01日 11:09
 */
@RestController
@Slf4j
public class OrderController {

    /**
     * 之前单机的时候这里是写死的,但是现在是搭建的是集群
     * 所以在使用的时候是填写的微服务的名称,让80端口号自己在注册中心去寻找到对应的服务端
     */
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalancer loadBalancer;

    /**
     * 对数据库写的操作
     *
     * @Date: 2021/7/1
     * @Description
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        System.out.println(PAYMENT_URL + "/payment/creat" + payment);
        return restTemplate.postForObject(PAYMENT_URL + "/payment/creat", payment, CommonResult.class);
        //PAYMENT_URL +"/payment/creat",payment
    }


    /**
     * 对数据库读的操作
     *
     * @Date: 2021/7/1
     * @Description 调用8001端口对数据库执行读的操作
     */
    @GetMapping(value = "/consumer/paymnet/get/{id}")       //请求访问的路径要和这里相同即可
    public CommonResult<Payment> getPayment(@PathVariable("id") long id) {

        System.out.println(PAYMENT_URL + "/payment/get/" + id);
        //这是本次请求的路径//http://CLOUD-PAYMENT-SERVICE/payment/get/31        只要get后面的参数能够成功的传递就你可以组成下面的路径
        //====================================================================================================================
        //PaymentController//http://localhost:8001/payment/get31            参数成功的传递后下面的路径就可以执行成功
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get" + id, CommonResult.class);//这里和生产者的路径相同即可
    }

    /**
     * getForObject  返回的属于json格式的字符串,现在返回要给更加详细的信息
     * restTemplate.getForEntity
     */
    @GetMapping(value = "/consumer/paymnet/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") long id) {

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get" + id, CommonResult.class);

        //根据状态码返回更加详细的信息
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }



    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {

        //通过注册中心的服务的名字,获取到服务器的数量
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if (instances == null || instances.size() <= 0) {
            return null;
        }
        //loadBalancer是手写的负载均衡的接口,通过里面的方法获取到服务器的实例
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }
}

