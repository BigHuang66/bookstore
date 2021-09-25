package com.huang.bookstore.dao.impl;

import com.huang.bookstore.dao.Dao;
import com.huang.bookstore.dao.UserDAO;
import com.huang.bookstore.domain.User;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 12:14
 */
public class UserDAOImpl extends Dao<User> implements UserDAO {
    @Override
    public User getUser(String userName) {
        String sql = "select userId, userName, accountId from userinfo where userName = ?";
        return get(sql, userName);
    }
}
