package com.sky.jSimple.aop;

import java.util.List;

public interface IProxyFactory {
   List<Proxy> create(Class<?> cls);
}
