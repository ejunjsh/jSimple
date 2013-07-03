package com.sky.jSimple.data;

import com.sky.jSimple.aop.Proxy;
import com.sky.jSimple.aop.ProxyChain;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.data.annotation.Transactional;
import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class TransactionalProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalProxy.class);
    
    

    public Object doProxy(ProxyChain proxyChain) throws JSimpleException  {
        Object result = null;
        boolean isTransactional = false; // 默认不具有事务
        
      //定义一个链接工厂
        SessionFactory sessionFactory=null;
        try {
            // 获取目标方法
            Method method = proxyChain.getTargetMethod();
            
            
            // 若在目标方法上定义了 @Transaction 注解，则说明该方法具有事务
            if (method.isAnnotationPresent(Transactional.class)) {
                // 设置为具有事务
                isTransactional = true;
                // 获取connectionFactory
               
                String connFactoryId= method.getAnnotation(Transactional.class).value();
                
                if(connFactoryId!=null&&!connFactoryId.isEmpty())
                {
                	sessionFactory=BeanContainer.getBean(connFactoryId);
                }
                if(sessionFactory==null)
                {
                	sessionFactory=BeanContainer.getBean(SessionFactory.class);
                }
                
                if(sessionFactory==null)
                {
                	logger.debug("not find connection");
                	 throw new JSimpleException("not find connection");
                }
                
                // 开启事务
                sessionFactory.beginTransaction();
              
                    logger.debug("begin transaction");
                
                // 执行操作
                result = proxyChain.doProxyChain();
                // 提交事务
                sessionFactory.commitTransaction();
               
                    logger.debug("commit transaction");
                
            } else {
                // 执行操作
                result = proxyChain.doProxyChain();
                
                //关闭链接
                List<SessionFactory> factories= BeanContainer.getBeans(SessionFactory.class);
                for(SessionFactory factory:factories)
                {
                	factory.remove();
                }
            }
        } catch (Exception e) {
            // 判断是否具有事务
            if (isTransactional) {
                // 回滚事务
            	sessionFactory.rollbackTransaction();
                if (logger.isDebugEnabled()) {
                    logger.debug("rollback transaction");
                }
            }
            else {
            	 //关闭链接
                List<SessionFactory> factories= BeanContainer.getBeans(SessionFactory.class);
                for(SessionFactory factory:factories)
                {
                	factory.remove();
                }
			}
            throw new JSimpleException(e);
        }
        return result;
    }
}
