package com.huang.bookstore.dao.impl;

import com.huang.bookstore.dao.Dao;
import com.huang.bookstore.dao.TradeDAO;
import com.huang.bookstore.domain.Trade;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 12:07
 */
public class TradeDAOImpl extends Dao<Trade> implements TradeDAO {

    /***
     * @description:  更新交易信息到数据库
     * @param: trade
     * @return: long
     */
    @Override
    public long insert(Trade trade) {
        String sql = "insert into trade (userId, tradeTime) values(?,?)";
        long tradeId = update(sql, trade.getUserId(), trade.getTradeTime());
        trade.setTradeId((int) tradeId);
        return tradeId;
    }

    /***
     * @description:  根据用户 id 获取交易
     * @param: userId
     * @return: java.util.Set<com.huang.bookstore.domain.Trade>
     */
    @Override
    public Set<Trade> getTradesWithUserId(int userId) {
        String sql = "select tradeId ,userId ,tradeTime from trade where userid = ? order by tradeTime desc";
        return new LinkedHashSet(getForList(sql, userId));
    }
}
