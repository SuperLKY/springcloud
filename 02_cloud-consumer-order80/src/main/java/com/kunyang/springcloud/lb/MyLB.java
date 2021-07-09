package com.kunyang.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KunYang
 * @date 2021年07月06日 14:36
 */
@Component
public class MyLB implements LoadBalancer {

    //这是一个原子类
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            //这个是请求次数的累计,通过请求的次数对服务器的数量进行取余,
            next = current >= 2147483647 ? 0 : current + 1;
            //一直进行自旋,直到取到我们想要的值  ,如果期望的值是我们想要的值就进行修改
        } while (!this.atomicInteger.compareAndSet(current, next));//如果是就写进AtomicInteger
        System.out.println("第几次访问的此时next: " + next);
        return next;//写进原子类后进行返回
    }

/**当前的第几次访问,对应到哪一个服务器由,取余的结果进行选择
 * rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标
 * serviceInstances 就是服务器的数量
 * @Date: 2021/7/6
 * @Description
 */
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
