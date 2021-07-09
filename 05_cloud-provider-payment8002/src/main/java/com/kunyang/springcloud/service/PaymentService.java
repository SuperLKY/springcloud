package com.kunyang.springcloud.service;

import com.kunyang.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    //对数据库的写的操作
    public int create(Payment payment);//返回值使用int的原因:如果语句执行成功返回大于0代表数据插入成功

    //对数据库读的操作
    public Payment getPaymentById(@Param("id") long id);

}
