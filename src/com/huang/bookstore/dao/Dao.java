package com.huang.bookstore.dao;

import com.huang.bookstore.web.ConnectionContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * 项目名称：Bookstore
 * 类名称：Dao
 * 类描述：强大的底层基类
 */
public class Dao<T> {

    /**
     * @Fields queryRunner : JDBC工具
     */
    private QueryRunner queryRunner = new QueryRunner();

    private Class<T> clazz;

    public Dao() {
        // 获取当前对象所在的类带泛型的父类 this是子类对象
        Type type = this.getClass().getGenericSuperclass();
        // 强转类型 ParameterizedType ：带泛型的类
        ParameterizedType paramtype = (ParameterizedType) type;
        // 拿到它的泛型
        Type[] typeArguments = paramtype.getActualTypeArguments();
        // 拿到了泛型的第一个参数
        clazz = (Class<T>) typeArguments[0];
    }

    /**
     * @Title: getForValue
     * @Description: 返回某个字段的值,或返回表中共有多少条记录 返回类型是Long
     */
    public <E> E getForValue(String sql,Object ...args) {

        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            return (E) queryRunner.query(conn, sql, new ScalarHandler() ,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: getForList
     * @Description: 返回对象的集合
     */
    public List<T> getForList(String sql,Object ...args){

        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            return queryRunner.query(conn, sql, new BeanListHandler<>(clazz),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: get
     * @Description: 返回单个对象
     */
    public T get(String sql,Object ...args) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            // 用 JDBC工具类查询数据库 然后把结果集构建成一个对象返回
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz) ,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: update
     * @Description: 插入数据、删除、修改  如果是insert 就返回被修改的id
     */
    public long update(String sql,Object ...args) {

        long id = 0;
        // 获取来自当前线程的 Conn
        Connection conn = ConnectionContext.getInstance().get();
        PreparedStatement ps = null;
        ResultSet rs = null;
        if("insert".equals(sql.substring(0,6))) {
            try {
                // 产生主键
                ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i+1, args[i]);
                }
                ps.executeUpdate();
                // 获取主键值
                rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    id = rs.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                return queryRunner.update(conn, sql, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (int) id;
    }

    /**
     * @Title: batch
     * @Description: 批量插入
     * new Object[] {1,1,1},new Object[] {2,2,2},new Object[] {3,3,3}
     *    	更新第一行的记录某两个字段的值为 1,1	另外两个同理
     *    	更新成功返回的是一个全为1的数组 哪条SQL不成功对应数组里面就为0
     */
    public int[] batch(String sql,Object[] ...params) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            // 批量插入
            return  queryRunner.batch(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
