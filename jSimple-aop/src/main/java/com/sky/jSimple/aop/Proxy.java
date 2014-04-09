package com.sky.jSimple.aop;

import com.sky.jSimple.exception.JSimpleException;

public interface Proxy {
    Object doProxy(ProxyChain aspectChain) throws JSimpleException ;
}
