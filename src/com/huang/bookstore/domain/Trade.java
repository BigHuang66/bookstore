package com.huang.bookstore.domain;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 交易信息
 * @date 2021/9/22 22:50
 */
public class Trade {

    private int tradeId;

    private int userId;

    private Timestamp tradeTime;

    private double totalMoney;

    private Set<TradeItem> items = new LinkedHashSet<TradeItem>();

    public Trade() {
    }

    public Trade(int tradeId, int userId, Timestamp tradeTime) {
        this.tradeId = tradeId;
        this.userId = userId;
        this.tradeTime = tradeTime;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Timestamp tradeTime) {
        this.tradeTime = tradeTime;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Set<TradeItem> getItems() {
        return items;
    }

    public void setItems(Set<TradeItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", userId=" + userId +
                ", tradeTime=" + tradeTime +
                ", totalMoney=" + totalMoney +
                ", items=" + items +
                '}';
    }
}
