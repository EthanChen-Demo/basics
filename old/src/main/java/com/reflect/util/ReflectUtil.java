package com.reflect.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
 
/**
 * 反射工具类
 * @author qbg
 *
 */
public abstract class ReflectUtil {
     
    /**
     * 获取某个对象的属性
     */
    public Object getProperty(Object owner, String fieldName) throws Exception {
        //1、得到该对象的Class。
        Class<?> ownerClass = owner.getClass();
        //2、通过Class得到类声明的属性。
        Field field = ownerClass.getField(fieldName);
        //3、通过对象得到该属性的实例，如果这个属性是私有的，这里就会抛出IllegalAccessException。
        Object property = field.get(owner); //此处获取的是对象的属性，所以传递的是owner。
        return property;
    }
     
    /**
     * 获取某个类的静态属性
     */
    public Object getStaticProperty(String className,String fieldName) throws Exception{
        //1、得到该类的Class
        Class<?> ownerClass = Class.forName(className);
        //2、通过Class得到类声明的属性
        Field field = ownerClass.getField(fieldName);
        //3、由于获取的是静态属性，此处传递的为Class，直接从Class中获取静态属性
        Object property = field.get(ownerClass); 
        return property;
    }
     
    /**
     * 执行某对象的方法
     */
    public Object invokeMethod(Object owner,String methodName,Object[] args) throws Exception{
        //1、获取对象的Class。
        return null;
    }
     
    /**
     * 判断是否为某个类的实例
     */
    public boolean isInstance(Object obj,Class<?> clazz){
        return clazz.isInstance(obj);
    }
     
    /**
     * 得到数组中的某个元素
     */
    public Object getByArray(Object array,int index){
        return Array.get(array, index);
    }

    /**
     * all public methods
     * @param targetClass
     * @return
     */
    public static List<Method> getAllMethod(Class<?> targetClass) {
        if (targetClass.getClass().equals(Object.class) || targetClass.getClass().isArray()) {
            return Lists.newArrayList();
        }
        List<Method> result = Lists.newArrayList();
        result.addAll(Lists.newArrayList(targetClass.getMethods()));
        
        return result;
    }
}

