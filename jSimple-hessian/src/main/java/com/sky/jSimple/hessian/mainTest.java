package com.sky.jSimple.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

public class mainTest {

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		String url = "http://localhost/jSimple-hessian/hello.do";
        HessianProxyFactory factory = new HessianProxyFactory();
        ITestService testService = (ITestService)factory.create(ITestService.class,url);
        System.out.print(testService.helloWorld("你好"));
	}

}
