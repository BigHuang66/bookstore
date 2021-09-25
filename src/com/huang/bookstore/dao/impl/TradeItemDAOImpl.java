package com.huang.bookstore.dao.impl;

import com.huang.bookstore.dao.Dao;
import com.huang.bookstore.dao.TradeItemDAO;
import com.huang.bookstore.domain.Trade;
import com.huang.bookstore.domain.TradeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/24 14:32
 */
public class TradeItemDAOImpl extends Dao<TradeItem> implements TradeItemDAO {

    @Override
    public boolean batchSave(Collection<TradeItem> items) {
        String sql = "insert into tradeitem (bookId, quantity, tradeId) values (?,?,?)";
        Object[][] params = new Object[items.size()][3];
        ArrayList<TradeItem> TradeItems = new ArrayList<>(items);
        for (int i = 0; i< items.size();i++) {
            // size 组 每组都填充 这笔订单的 BookID、数量、交易流水号
            params[i][0] = TradeItems.get(i).getBookId();
            params[i][1] = TradeItems.get(i).getQuantity();
            params[i][2] = TradeItems.get(i).getTradeId();
        }
        int[] batch = batch(sql, params);
        for (int i : batch) {
            // 这个 -2是我测试看到的 不一定总是 -2哦
            if(i != -2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<TradeItem> getTradeItemsWithTradeId(int tradeId) {
        String sql = "select itemId, bookId, quantity, tradeId from tradeitem where tradeId = ?";
        return new HashSet<>(getForList(sql, tradeId));
    }
}
