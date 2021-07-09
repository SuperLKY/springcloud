package com.kunyang.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KunYang
 * @date 2021年07月08日 18:53
 */
@Configuration
public class GateWayConfig {

    /**
     * 配置了一个id为route-name的路由规则，
     * 当访问地址 http://localhost:9527/guonei时会自动转发到地址：http://news.baidu.com/guonei
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        RouteLocatorBuilder.Builder routes = builder.routes();
        //建立一个新的路由访问规则
        routes.route("path_route_atguigu", //等同于yml里面自定义个IP
                r -> r.path("/guonei")//当访问9527端口的时候将会路由到下面的地址
                        .uri("http://news.baidu.com/guonei")).build();

        return routes.build();

    }
}
