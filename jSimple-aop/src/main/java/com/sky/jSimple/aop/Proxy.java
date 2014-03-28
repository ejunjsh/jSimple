package com.sky.jSimple.aop;

public interface Proxy {
    Object doProxy(AspectChain aspectChain) throws Throwable;
}
