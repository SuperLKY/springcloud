package com.kunyang.springcloud;

import com.kunyang.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效，形如：
 * @Date: 2021/7/5
 * @Description
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration= MySelfRule.class) //不要使用默认的轮询,使用自定义的随机策略
public class MainApp80 {
    public static void main(String[] args) {
        SpringApplication.run(MainApp80.class, args);
    }
}