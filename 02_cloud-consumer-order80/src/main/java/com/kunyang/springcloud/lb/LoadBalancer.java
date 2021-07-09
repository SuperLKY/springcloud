package com.kunyang.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @auther zzyy
 * @create 2020-02-02 17:57
 */
public interface LoadBalancer {
    /**
     * 定义一个接口,获取机器的总数,放到list集合中
     * @param serviceInstances
     * @return
     */
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
 