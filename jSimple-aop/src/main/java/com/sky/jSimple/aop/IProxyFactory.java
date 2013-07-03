package com.sky.jSimple.aop;

import com.sky.jSimple.exception.JSimpleException;

import java.util.List;

public interface IProxyFactory {
   List<Proxy> create(Class<?> cls) throws JSimpleException;
}
