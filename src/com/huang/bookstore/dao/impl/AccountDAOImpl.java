package com.huang.bookstore.dao.impl;

import com.huang.bookstore.dao.AccountDAO;
import com.huang.bookstore.dao.Dao;
import com.huang.bookstore.domain.Account;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 11:04
 */
public class AccountDAOImpl extends Dao<Account> implements AccountDAO {

    @Override
    public Account get(int accountId) {
        String sql = "select accountid, balance from account where accountid = ?";
        return get(sql, accountId);
    }

    /***
     * @description: 为账户扣除金额
     * @return: long
     */
    @Override
    public long updateBalance(int accountId, double amount) {
        String sql = "update account set balance = balance - ? where accountid = ?";
        return update(sql, amount, accountId);
    }
}
