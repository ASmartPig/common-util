package com.itest.developer.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckDataUtil {

    /**
     *判断一个实体类对象实例的所有成员变量是否为空
     *@param obj 校验的类对象实例
     *@return List
     *@throws Exception
     */

    public static List<String> isObjectFieldEmpty(Object obj ,List<String> excludeNames) throws Exception {
        //得到类对象
        Class<?> clazz=obj.getClass();
        //得到属性集合
        Field[] fs=clazz.getDeclaredFields();
        List<String> list=new ArrayList<String>();
        //遍历属性
        for(Field field:fs){
            //设置属性是可以访问的（私有的也可以）
            field.setAccessible(true);
            if(excludeNames.contains(field.getName()) && ( null == field.get(obj) || "".equals(field.get(obj).toString()) )){
                String name= field.getName();
                list.add(name);
            }
        }
        return list;
    }

    public static List<String> isObjectFieldEmpty(Object obj) throws Exception {
        //得到类对象
        Class<?> clazz=obj.getClass();
        //得到属性集合
        Field[] fs=clazz.getDeclaredFields();
        List<String> list= Lists.newArrayList();
        //遍历属性
        for(Field field:fs){
            //设置属性是可以访问的（私有的也可以）
            field.setAccessible(true);
            if( null == field.get(obj) || "".equals(field.get(obj).toString()) ){
                String name=field.getName();
                list.add(name);
            }
        }
        return list;
    }

    public static Object checkParam(Object... params ){

        for (Object obj : params) {
            if (Objects.isNull(obj)) {
                return obj;
            }
        }

        return null;
    }
}
