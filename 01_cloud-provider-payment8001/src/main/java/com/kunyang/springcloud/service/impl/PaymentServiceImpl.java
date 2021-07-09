package com.kunyang.springcloud.service.impl;

import com.kunyang.springcloud.dao.PaymentDao;
import com.kunyang.springcloud.entities.Payment;
import com.kunyang.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author KunYang
 * @date 2021年06月30日 20:37
 * service实现类
 * 先把@service注解写上去
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    //对数据库的写的操作
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    //对数据库读的操作
    public Payment getPaymentById(long id) {
        return paymentDao.getPaymentById(id);

    }

}
