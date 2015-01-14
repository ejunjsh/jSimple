package com.sky.jSimple.data;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionFactory {

    private final Logger logger = LoggerFactory.getLogger(SessionFactory.class);
    // 定义一个局部线程变量（使每个线程都拥有自己的连接）
    private final ThreadLocal<Session> connContainer = new ThreadLocal<Session>();
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Session getSession() throws JSimpleException {
        Connection conn;
        Session session;
        try {
            // 先从 ThreadLocal 中获取 Session
            session = connContainer.get();
            if (session == null) {
                // 若不存在，则从 DataSource 中获取 Connection,并封装Session
                conn = dataSource.getConnection();
                session = new Session();
                session.setConnection(conn);
                session.setDataSource(dataSource);
                session.setSessionFactory(this);
                // 将 Session 放入 ThreadLocal 中
                if (session != null) {
                    connContainer.set(session);
                }
            }
        } catch (SQLException e) {
            logger.error("获取数据库连接出错！", e);
            throw new JSimpleException(e);
        }
        return session;
    }

    // 开启事务
    public void beginTransaction() {
        Session session = getSession();
        if (session != null) {
            try {
                session.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("开启事务出错！", e);
                throw new JSimpleException(e);
            } finally {
                connContainer.set(session);
            }
        }
    }

    // 提交事务
    public void commitTransaction() {
        Session session = getSession();
        if (session != null) {
            try {
                session.commit();
                session.close();
            } catch (SQLException e) {
                logger.error("提交事务出错！", e);
                throw new JSimpleException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

    // 回滚事务
    public void rollbackTransaction() {
        Session session = getSession();
        if (session != null) {
            try {
                session.rollback();
                session.close();
            } catch (SQLException e) {
                logger.error("回滚事务出错！", e);
                throw new JSimpleException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

    public void remove() {
        Session session;
        try {
            // 先从 ThreadLocal 中获取 Connection
            session = connContainer.get();
            if (session != null && session.getConnection() != null && !session.getConnection().isClosed()) {
                session.getConnection().close();
            }
            connContainer.remove();
        } catch (SQLException e) {
            logger.error("移除数据库连接出错！", e);
            throw new JSimpleException(e);
        }
    }
}
