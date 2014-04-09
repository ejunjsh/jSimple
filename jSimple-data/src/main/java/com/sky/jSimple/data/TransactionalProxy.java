package com.sky.jSimple.data;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;

public class TransactionalProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws JSimpleException  {
        Object result = null;
        boolean isTransactional = false; // 默认不具有事务
        try {
            // 获取目标方法
            Method method = proxyChain.getTargetMethod();
            // 若在目标方法上定义了 @Transaction 注解，则说明该方法具有事务
            if (method.isAnnotationPresent(Transactional.class)) {
                // 设置为具有事务
                isTransactional = true;
                // 开启事务
                DBHelper.beginTransaction();
              
                    logger.debug("[Smart] begin transaction");
                
                // 执行操作
                result = proxyChain.doProxyChain();
                // 提交事务
                DBHelper.commitTransaction();
               
                    logger.debug("[Smart] commit transaction");
                
            } else {
                // 执行操作
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            // 判断是否具有事务
            if (isTransactional) {
                // 回滚事务
                DBHelper.rollbackTransaction();
                if (logger.isDebugEnabled()) {
                    logger.debug("[Smart] rollback transaction");
                }
            }
            throw new JSimpleException(e);
        }
        return result;
    }
}
