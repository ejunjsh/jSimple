package com.sky.jSimple.aop;

public interface Aspect {
    Object doAspect(AspectChain aspectChain) throws Throwable;
}
