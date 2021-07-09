模块的访问的地址
02_cloud-consumer-order80(OrderController){

    对数据库添加的操作:      http://localhost:80/consumer/payment/create?serial=世界那么大我想去看
    对数据库执行读的操作:    http://localhost:80/consumer/paymnet/get/31
    数据库读的操作,返回一个更加详细的信息: http://localhost:80/consumer/paymnet/getForEntity/31000

    }

10_cloud-consumer-feign-order80(OrderFeignController){
                         http://localhost:80/consumer/payment/get/31

}


12_cloud-consumer-feign-hystrix-order80(OrderHystirxController){
1.服务的超时或者服务调用的异常,指定一个进行兜底的方法
2.为了避免每个方法都制定一个兜底的方法造成代码的膨胀,所以指定一个通用的兜底的方法
3.为了业务逻辑的混乱,代码耦合度高的问题
4.服务的熔断就是在指定的时间内,在指定的次数里面,捕获到的异常书达到多少后就会 打开断路器,断路器打开后再输入正确的访问也会显示捕捉到异常的提示信息
  当访问的正确率上来后,由半开的状态变成全开的状态
    (断路器的三要数)(断路器打开的和关闭的条件)
    熔断器的三种状态:1.错误的异常过多的被捕获到,熔断器打开了,就不会调用主逻辑,而是出发降级的方法
                 2.熔断的半开:当访问的时候,正确率慢慢的上来就会导致熔断的恢复
                 3.(链路的自动恢复)当正确的访问达到一定后就会把熔断器给关闭,

                    测试一:直接响应的地址:http://localhost/consumer/payment/hystrix/ok/31
                    测试二:延迟响应的地址:http://localhost:80/consumer/payment/hystrix/timeout/31
                    测试三:指定响应超时时间是5秒,规定三秒执行,服务降级兜底的方法就会被执行
                                        http://localhost:8001/payment/hystrix/timeout/31
}


