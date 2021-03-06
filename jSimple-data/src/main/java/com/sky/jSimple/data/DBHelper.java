package com.sky.jSimple.data;

import com.sky.jSimple.data.annotation.Entity;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.ArrayUtil;
import com.sky.jSimple.utils.MapUtil;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBHelper {

    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    private static QueryRunner queryRunner = new QueryRunner();


    // 执行查询（返回一个对象）
    public static <T> T queryBean(Session session, Class<T> cls, String sql, Object... params) {
        T result;
        try {
            Map<String, String> fieldMap = EntityHelper.getEntityMap().get(cls.getAnnotation(Entity.class).value());
            if (MapUtil.isNotEmpty(fieldMap)) {
                result = queryRunner.query(session.getConnection(), sql, new BeanHandler<T>(cls, new BasicRowProcessor(new JSimpleDataBeanProcessor(session.getSessionFactory(), fieldMap))), params);
            } else {
                result = queryRunner.query(session.getConnection(), sql, new BeanHandler<T>(cls, new BasicRowProcessor(new JSimpleDataBeanProcessor(session.getSessionFactory()))), params);
            }
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 执行查询（返回多个对象）
    public static <T> List<T> queryBeanList(Session session, Class<T> cls, String sql, Object... params) {
        List<T> result;
        try {
            Map<String, String> fieldMap = EntityHelper.getEntityMap().get(cls.getAnnotation(Entity.class).value());
            if (MapUtil.isNotEmpty(fieldMap)) {
                result = queryRunner.query(session.getConnection(), sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new JSimpleDataBeanProcessor(session.getSessionFactory(), fieldMap))), params);
            } else {
                result = queryRunner.query(session.getConnection(), sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new JSimpleDataBeanProcessor(session.getSessionFactory()))), params);
            }
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 执行更新（包括 UPDATE、INSERT、DELETE）
    public static int update(Session session, String sql, Object... params) {
        int result;
        try {
            result = queryRunner.update(session.getConnection(), sql, params);
        } catch (SQLException e) {
            logger.error("更新出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 执行查询（返回 count 结果）
    public static long queryCount(Session session, String sql, Object... params) {
        long result;
        try {
            result = queryRunner.query(session.getConnection(), sql, new ScalarHandler<Long>("count(*)"), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询映射列表
    public static List<Map<String, Object>> queryMapList(Session session, String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            result = queryRunner.query(session.getConnection(), sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询单列数据（返回一个对象）
    public static <T> T queryColumn(Session session, String column, String sql, Object... params) {
        T result;
        try {
            result = queryRunner.query(session.getConnection(), sql, new ScalarHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询单列数据（返回多个对象）
    public static <T> List<T> queryColumnList(Session session, String column, String sql, Object... params) {
        List<T> result;
        try {
            result = queryRunner.query(session.getConnection(), sql, new ColumnListHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 插入（返回自动生成的主键）
    public static Serializable insertReturnPK(Session session, String sql, Object... params) {
        Serializable key = null;
        printSQL(sql);
        try {
            PreparedStatement pstmt = session.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (ArrayUtil.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            int rows = pstmt.executeUpdate();
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    key = (Serializable) rs.getObject(1);
                }
            }
        } catch (SQLException e) {
            logger.error("插入出错！", e);
            throw new JSimpleException(e);
        }

        return key;
    }

    //基础查询
    public static ResultSet query(Session session, String sql, Object... params) {
        printSQL(sql);
        try {
            PreparedStatement pstmt = session.getConnection().prepareStatement(sql);
            if (ArrayUtil.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            logger.error("插入出错！", e);
            throw new JSimpleException(e);
        }
    }

    //基础执行SQL
    public static int execute(Session session, String sql, Object... params) {
        printSQL(sql);
        try {
            PreparedStatement pstmt = session.getConnection().prepareStatement(sql);
            if (ArrayUtil.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("插入出错！", e);
            throw new JSimpleException(e);
        }
    }

    private static void printSQL(String sql) {
        logger.debug("[jSimple] SQL - {}", sql);
    }
}
