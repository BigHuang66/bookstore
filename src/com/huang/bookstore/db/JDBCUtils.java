package com.huang.bookstore.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 10:45
 */
public class JDBCUtils {

    private static DataSource source = null;

    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("druid.properties");
            if (is == null) {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            }
            Properties pros = new Properties();
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return source.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void releaseConnection(Connection conn, PreparedStatement ps) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void releaseConnection(Connection conn,PreparedStatement ps, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
