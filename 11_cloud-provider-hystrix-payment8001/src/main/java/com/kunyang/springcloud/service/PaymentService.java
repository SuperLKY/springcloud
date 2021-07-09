package com.kunyang.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author KunYang
 * @date 2021年07月07日 19:58
 */
@Service
public class PaymentService {
    /**
     * 正常访问，一切OK
     *
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        //当前处理的线程池
        return "线程池:" + Thread.currentThread().getName() + "    paymentInfo_OK,id: " + id + "\t" + "O(∩_∩)O";
    }


    /**
     * 超时访问，演示降级
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            //下面的代码就是下面线程的时间是3秒,3秒以内就是正常的逻辑,三秒以内就会走正常的业务逻辑
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "9000")
    })
    public String paymentInfo_TimeOut(Integer id) {

//        int age =10/0;//当这个方法出现异常的时候下面兜底的方法也会被执行
        try {
            TimeUnit.SECONDS.sleep(3);//这里设置的是五秒钟的时间,会超过上面设置的时间,所以下面兜底的方法就会被执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "     paymentInfo_TimeOut,id: " + id + "\t" + "O(∩_∩)O，耗费3秒";
    }

    /**
     * 当上面的方法出了问题的时候,这个方法会被执行
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "/(ㄒoㄒ)/调用支付接口超时或异常：\t" + "\t当前线程池名字" + Thread.currentThread().getName();
    }


    //============================服务熔断
    /**
     *当错误率达到后就会开启断路器,就是一直执行的被捕获的异常
     * 当访问的正确率上来后链路就会慢慢的恢复,错误率的下降就会从半开的状态变成全开
     * @Date: 2021/7/8
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期比如说10秒钟,这是设定的时间范围内
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//10次里面的失败率达到多少后跳闸(10次内访问后抛出的异常次数)
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {

        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();//等同于之前使用的uuid,.toString,

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }

    /**
     * 如果上面的方法出事了,由这个方法会兜底的处理
     *
     * @Date: 2021/7/8
     */
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }
}
