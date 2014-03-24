package com.sky.jSimple.aop.test;

import java.util.List;
import java.util.ArrayList;

import com.sky.jSimple.aop.Aspect;
import com.sky.jSimple.aop.AspectFactory;
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
	  List<Aspect> ascpeList=new ArrayList<Aspect>();
	  ascpeList.add(aspect);
	  ascpeList.add(aspect2);
	  test test= AspectFactory.createAspect(test.class,ascpeList);
	  test.test(1);
	}

}
