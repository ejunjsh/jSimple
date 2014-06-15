package com.sky.jSimple.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.exception.JSimpleException;

public class ConnectionFactory {
	 
	private  final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	
	  private DataSource dataSource;
	   
	   // 定义一个局部线程变量（使每个线程都拥有自己的连接）
	  private  final ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	  
	  
	  public  Connection getConnection() throws JSimpleException {
	        Connection conn;
	        try {
	            // 先从 ThreadLocal 中获取 Connection
	            conn = connContainer.get();
	            if (conn == null) {
	                // 若不存在，则从 DataSource 中获取 Connection
	                conn = dataSource.getConnection();
	                // 将 Connection 放入 ThreadLocal 中
	                if (conn != null) {
	                    connContainer.set(conn);
	                }
	            }
	        } catch (SQLException e) {
	            logger.error("获取数据库连接出错！", e);
	            throw new JSimpleException(e);
	        }
	        return conn;
	    }
	  
	  // 开启事务
	    public  void beginTransaction() throws JSimpleException {
	        Connection conn = getConnection();
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(false);
	            } catch (SQLException e) {
	                logger.error("开启事务出错！", e);
	                throw new JSimpleException(e);
	            } finally {
	                connContainer.set(conn);
	            }
	        }
	    }

	    // 提交事务
	    public  void commitTransaction() throws JSimpleException {
	        Connection conn = getConnection();
	        if (conn != null) {
	            try {
	                conn.commit();
	                conn.close();
	            } catch (SQLException e) {
	                logger.error("提交事务出错！", e);
	                throw new JSimpleException(e);
	            } finally {
	                connContainer.remove();
	            }
	        }
	    }

	    // 回滚事务
	    public  void rollbackTransaction() throws JSimpleException {
	        Connection conn = getConnection();
	        if (conn != null) {
	            try {
	                conn.rollback();
	                conn.close();
	            } catch (SQLException e) {
	                logger.error("回滚事务出错！", e);
	                throw new JSimpleException(e);
	            } finally {
	                connContainer.remove();
	            }
	        }
	    }
}
