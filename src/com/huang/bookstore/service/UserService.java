package com.huang.bookstore.service;

import com.huang.bookstore.dao.BookDAO;
import com.huang.bookstore.dao.TradeDAO;
import com.huang.bookstore.dao.TradeItemDAO;
import com.huang.bookstore.dao.UserDAO;
import com.huang.bookstore.dao.impl.BookDAOImpl;
import com.huang.bookstore.dao.impl.TradeDAOImpl;
import com.huang.bookstore.dao.impl.TradeItemDAOImpl;
import com.huang.bookstore.dao.impl.UserDAOImpl;
import com.huang.bookstore.domain.Book;
import com.huang.bookstore.domain.Trade;
import com.huang.bookstore.domain.TradeItem;
import com.huang.bookstore.domain.User;

import java.util.Set;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 用户工具类
 * @date 2021/9/25 15:06
 */
public class UserService {

    private UserDAO userDAO = new UserDAOImpl();

    private TradeDAO tradeDAO = new TradeDAOImpl();

    private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

    private BookDAO bookDAO = new BookDAOImpl();

    public User getUserByUserName(String userName) {
        return userDAO.getUser(userName);
    }

    public User getUserWithTrades(String userName) {
        // 由用戶名拿到 用户对象
        User user = userDAO.getUser(userName);
        if (user == null) {
            return null;
        }

        int userId = user.getUserId();
        // 由用户id 拿到所有交易
        Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
        if (trades != null) {
            for (Trade trade : trades) {
                // 拿到每笔交易下的每个交易项
                Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(trade.getTradeId());
                if (items != null) {
                    for (TradeItem item : items) {
                        Book book = bookDAO.getBook(item.getBookId());
                        trade.setTotalMoney(trade.getTotalMoney() + Double.parseDouble(book.getPrice()) * item.getQuantity());
                        // 上述get是拿不到book的，可以从sql中看到
                        item.setBook(book);
                    }
                    trade.setItems(items);
                }
            }
        }
        user.setTrades(trades);
        return user;
    }

}
