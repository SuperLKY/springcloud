package com.kunyang.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;

/**
 * @author KunYang
 * @date 2021年07月05日 11:28
 */
public class MySelfRule {

    /**这这里定义了策略后,需要在主启动类的上面添加注解使其生效
     * @Date: 2021/7/5
     * @Description
     */
    @Bean
    public IRule myRule() {
        return new RandomRule();//定义为随机
    }
 }