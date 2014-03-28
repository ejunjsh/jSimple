package com.sky.jSimple.hessian;

import com.caucho.hessian.server.HessianServlet;

public class TestService extends HessianServlet implements ITestService {

	public String helloWorld(String hello) {
		return hello +" world";
	}

}
