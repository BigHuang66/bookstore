package com.huang.bookstore.domain;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/22 22:21
 */
public class Account {

    private int accountId;

    private int balance;

    public Account() {
    }

    public Account(int accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
