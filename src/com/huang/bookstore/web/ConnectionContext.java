package com.huang.bookstore.web;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 10:23
 */
public class ConnectionContext {
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    private ConnectionContext() {}

    private static ConnectionContext instance = new ConnectionContext();

    public static ConnectionContext getInstance() {
        return instance;
    }

    /***
     * @description:  链接绑定线程
     * @param: conn
     * @return: void
     */
    public void bind(Connection conn) {
        connectionThreadLocal.set(conn);
    }

    /***
     * @description:  获取当前链接的 connection
     * @param:
     * @return: java.sql.Connection
     */
    public Connection get() {
        return connectionThreadLocal.get();
    }

    /***
     * @description:  解绑
     * @param:
     * @return: void
     */
    public void remove() {
        try {
            instance.get().commit();
        } catch (SQLException e) {
            System.out.println("数据库提交异常");
        }
        connectionThreadLocal.remove();
    }
}
