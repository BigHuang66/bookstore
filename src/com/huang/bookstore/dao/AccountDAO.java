package com.huang.bookstore.dao;

import com.huang.bookstore.domain.Account;

public interface AccountDAO {

    Account get(int accountId);

    /***
     * @description:  更新成功返回 1
     * @param: accountId
     *         amount
     * @return: long
     */
    long updateBalance(int accountId, double amount);

}
