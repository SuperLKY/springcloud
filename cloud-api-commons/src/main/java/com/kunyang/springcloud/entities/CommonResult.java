package com.kunyang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KunYang
 * @date 2021年06月30日 18:18
 * 返回前端通用的JSON格式的字符串类
 * 这个类用于返回后台的数据给前端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {//

    //404 not_found
    private Integer code;//状态码
    private String message;//成功
    private T       data;//展示这个类的全部的信息属性

    //因为有可能这个T是个null所以定义两个参数的构造器
    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}
