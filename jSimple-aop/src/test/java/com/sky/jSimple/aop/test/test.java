package com.sky.jSimple.aop.test;

import java.util.List;
import java.util.ArrayList;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.AOPFactory;
import com.sky.jSimple.aop.annotation.Cache;
import com.sky.jSimple.aop.annotation.Log;

public class test {
    
	@Log
	@Cache
	public void test(int j)
	{
		this.i++;
		System.out.println(this.i+j);
	}
	
	private int i=0;
	
	public static void main(String[] args) {
	  LogAspect aspect=new LogAspect();	
	  CacheAspect aspect2=new CacheAspect();
	  List<Proxy> ascpeList=new ArrayList<Proxy>();
	  ascpeList.add(aspect);
	  ascpeList.add(aspect2);
	  test test= AOPFactory.createEnhanceObject(test.class,ascpeList);
	  test.test(1);
	}

}
