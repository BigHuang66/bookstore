package com.huang.bookstore.service;

import com.huang.bookstore.dao.AccountDAO;
import com.huang.bookstore.dao.impl.AccountDAOImpl;
import com.huang.bookstore.domain.Account;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 余额查询
 * @date 2021/9/25 15:05
 */
public class AccountService {
    private AccountDAO accountDao = new AccountDAOImpl();

    public Account getAccount(int accountId) {
        return accountDao.get(accountId);
    }
}
