package com.huang.bookstore.dao;

import com.huang.bookstore.domain.Trade;

import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 10:37
 */
public interface TradeDAO {

    long insert(Trade trade);

    Set<Trade> getTradesWithUserId(int userId);
}
