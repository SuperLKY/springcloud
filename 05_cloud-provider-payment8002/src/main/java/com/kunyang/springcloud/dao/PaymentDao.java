package com.kunyang.springcloud.dao;

import com.kunyang.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *    dao层主要就是对数据库的写和读的操作
 */
@Mapper  //在之前用的是@Repository
public interface PaymentDao {

    //对数据库的写的操作,就是插入
    public int create(Payment payment);//返回值使用int的原因:如果语句执行成功返回的是数据库中表搜影响的行数

    //对数据库读的操作
    public Payment getPaymentById(@Param("id") long id);

}
