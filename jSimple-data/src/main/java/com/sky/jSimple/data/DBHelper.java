package com.sky.jSimple.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.ArrayUtil;
import com.sky.jSimple.utils.MapUtil;
import com.sky.jSimple.utils.StringUtil;

public class DBHelper {

	private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    private static String databaseType;
    private static QueryRunner queryRunner;

    static {
        databaseType = jSimpleConfig.getConfigString("jdbc.type");
        if (StringUtil.isNotEmpty(databaseType)) {
            queryRunner = new QueryRunner();
        }
    }

    // 获取数据库类型
    public static String getDatabaseType() {
        return databaseType;
    }

    // 执行查询（返回一个对象）
    public static <T> T queryBean(Session session,Class<T> cls, String sql, Object... params) throws JSimpleException {
        T result;
        try {
            Map<String, String> fieldMap = EntityHelper.getEntityMap().get(cls);
            if (MapUtil.isNotEmpty(fieldMap)) {
                result = queryRunner.query(session.getConnection(),sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(fieldMap))), params);
            } else {
                result = queryRunner.query(session.getConnection(),sql, new BeanHandler<T>(cls), params);
            }
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 执行查询（返回多个对象）
    public static <T> List<T> queryBeanList(Session session,Class<T> cls, String sql, Object... params) throws JSimpleException {
        List<T> result;
        try {
            Map<String, String> fieldMap = EntityHelper.getEntityMap().get(cls);
            if (MapUtil.isNotEmpty(fieldMap)) {
                result = queryRunner.query(session.getConnection(),sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(fieldMap))), params);
            } else {
                result = queryRunner.query(session.getConnection(),sql, new BeanListHandler<T>(cls), params);
            }
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 执行更新（包括 UPDATE、INSERT、DELETE）
	public static int update(Session session,String sql, Object... params) throws JSimpleException {
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
    public static long queryCount(Session session,String sql, Object... params) throws JSimpleException {
        long result;
        try {
            result = queryRunner.query(session.getConnection(),sql, new ScalarHandler<Long>("count(*)"), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询映射列表
    public static List<Map<String, Object>> queryMapList(Session session,String sql, Object... params) throws JSimpleException {
        List<Map<String, Object>> result;
        try {
            result = queryRunner.query(session.getConnection(),sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询单列数据（返回一个对象）
    public static <T> T queryColumn(Session session,String column, String sql, Object... params) throws JSimpleException {
        T result;
        try {
            result = queryRunner.query(session.getConnection(),sql, new ScalarHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 查询单列数据（返回多个对象）
    public static <T> List<T> queryColumnList(Session session,String column, String sql, Object... params) throws JSimpleException {
        List<T> result;
        try {
            result = queryRunner.query(session.getConnection(),sql, new ColumnListHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new JSimpleException(e);
        }
        printSQL(sql);
        return result;
    }

    // 插入（返回自动生成的主键）
    public static Serializable insertReturnPK(Session session,String sql, Object... params) throws JSimpleException {
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

    private static void printSQL(String sql) {
        logger.debug("[jSimple] SQL - {}", sql);
    }
}
