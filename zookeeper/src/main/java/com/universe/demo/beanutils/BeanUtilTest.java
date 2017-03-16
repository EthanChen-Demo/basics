package com.universe.demo.beanutils;

import org.springframework.beans.BeanUtils;

public class BeanUtilTest {
	public static void main(String[] args) {
		FromBean fromBean = new FromBean();
		fromBean.setAddress("北京市朝阳区大屯路");
		fromBean.setAge(20);
		fromBean.setMoney(30000.111);
		fromBean.setIdno("110330219879208733");
		fromBean.setName("测试");
		
		ToBean toBean = new ToBean();
		BeanUtils.copyProperties(fromBean, toBean);
		
		System.out.println("::::" + toBean.toString());
		
	}
}
