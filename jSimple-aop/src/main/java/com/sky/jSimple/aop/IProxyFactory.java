package com.sky.jSimple.aop;

import java.util.List;

import com.sky.jSimple.exception.JSimpleException;

public interface IProxyFactory {
   List<Proxy> create(Class<?> cls) throws JSimpleException;
}
