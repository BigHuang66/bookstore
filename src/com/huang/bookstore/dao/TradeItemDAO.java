package com.huang.bookstore.dao;

import com.huang.bookstore.domain.TradeItem;

import java.util.Collection;
import java.util.Set;

public interface TradeItemDAO {

    boolean batchSave(Collection<TradeItem> items);

    Set<TradeItem> getTradeItemsWithTradeId(int tradeId);
}
