package com.huang.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/22 22:49
 */
public class User {

    private int userId;

    private String userName;

    private int accountId;

    private Set<Trade> trades = new LinkedHashSet<Trade>();

    public User() {
    }

    public User(int userId, String userName, int accountId, Set<Trade> trades) {
        this.userId = userId;
        this.userName = userName;
        this.accountId = accountId;
        this.trades = trades;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", accountId=" + accountId +
                ", trades=" + trades +
                '}';
    }
}
