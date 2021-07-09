package com.kunyang.springcloud.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 对GateWay的filter进行自定义个
 * 首先继承两个接口,然后对方法进行实现
 *
 * @author KunYang
 * @date 2021年07月08日 21:16
 */
@Configuration
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    /**
     *定义一个filter在访问网关的时候进行过滤,访问的条件是自定义的
     *当访问网关的时候会检查有没有这个条件,要是有这个条件就会放行到下一条的过虑链,要是没有符合条件
     * 下面的过滤的条件就是检查请求访问的时候有没有带着这个uname的key
     * @Date: 2021/7/8
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //exchange 通过它就可以获取到请求对象和响应对象
        System.out.println("time:" + new Date() + "\t 执行了自定义的全局过滤器: " + "MyLogGateWayFilter" + "hello");

        String uname = exchange.getRequest().getQueryParams().getFirst("uname");//通过请求对象获取到携带的参数,参数要是uname
        if (uname == null) {
            System.out.println("****用户名为null，无法登录");
            //获取到响应对象,返回一个不能接受的状态码
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        //如果当前的判断是合法的那就进入到下一个过滤链
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }

}










