package com.reflect.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
 
/**
 * ���乤����
 * @author qbg
 *
 */
public abstract class ReflectUtil {
     
    /**
     * ��ȡĳ�����������
     */
    public Object getProperty(Object owner, String fieldName) throws Exception {
        //1���õ��ö����Class��
        Class<?> ownerClass = owner.getClass();
        //2��ͨ��Class�õ������������ԡ�
        Field field = ownerClass.getField(fieldName);
        //3��ͨ������õ������Ե�ʵ����������������˽�еģ�����ͻ��׳�IllegalAccessException��
        Object property = field.get(owner); //�˴���ȡ���Ƕ�������ԣ����Դ��ݵ���owner��
        return property;
    }
     
    /**
     * ��ȡĳ����ľ�̬����
     */
    public Object getStaticProperty(String className,String fieldName) throws Exception{
        //1���õ������Class
        Class<?> ownerClass = Class.forName(className);
        //2��ͨ��Class�õ�������������
        Field field = ownerClass.getField(fieldName);
        //3�����ڻ�ȡ���Ǿ�̬���ԣ��˴����ݵ�ΪClass��ֱ�Ӵ�Class�л�ȡ��̬����
        Object property = field.get(ownerClass); 
        return property;
    }
     
    /**
     * ִ��ĳ����ķ���
     */
    public Object invokeMethod(Object owner,String methodName,Object[] args) throws Exception{
        //1����ȡ�����Class��
        return null;
    }
     
    /**
     * �ж��Ƿ�Ϊĳ�����ʵ��
     */
    public boolean isInstance(Object obj,Class<?> clazz){
        return clazz.isInstance(obj);
    }
     
    /**
     * �õ������е�ĳ��Ԫ��
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

