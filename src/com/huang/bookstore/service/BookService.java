package com.huang.bookstore.service;

import com.huang.bookstore.dao.*;
import com.huang.bookstore.dao.impl.*;
import com.huang.bookstore.domain.*;
import com.huang.bookstore.web.CriteriaBook;
import com.huang.bookstore.web.Page;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 工具类
 * @date 2021/9/25 14:22
 */
public class BookService {

    private BookDAO bookDao = new BookDAOImpl();

    private AccountDAO accountDao = new AccountDAOImpl();

    private TradeDAO tradeDAO = new TradeDAOImpl();

    private UserDAO userDAO = new UserDAOImpl();

    private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

    public Page<Book> getPage(CriteriaBook cb) {
        return bookDao.getPage(cb);
    }

    public Book getBook(int id) {
        return bookDao.getBook(id);
    }

    public boolean addToCart(int id, ShoppingCar sc) {
        Book book = bookDao.getBook(id);
        if (book != null) {
            sc.addBook(book);
            return true;
        }
        return false;
    }

    public void removeItemFromShoppingCar(ShoppingCar sc, int id) {
        sc.removeItem(id);
    }

    public static void clearShoppingCar(ShoppingCar sc) {
        sc.clear();
    }

    public void updateItemQuantity(ShoppingCar sc, int id, int quantity) {
        sc.updateItemQuantity(id, quantity);
    }

    /***
     * @description: 结账
     * @return:
     */
    public void cash(ShoppingCar sc, String userName, String accountId) {

        // 根据购物车内容 更新数据库中存量、销售量
        bookDao.batchUpdateStoreNumberAndSalesAmount(sc.getItems());

        // 更新账户余额
        accountDao.updateBalance(Integer.parseInt(accountId), sc.getTotalMoney());

        // 添加 交易信息 到交易记录 trade表中
        Trade trade = new Trade();
        trade.setTradeTime(new Timestamp(new java.util.Date().getTime()));
        trade.setUserId(userDAO.getUser(userName).getUserId());
        tradeDAO.insert(trade);

        // 添加 交易项 到交易tradeItem表中
        Collection<TradeItem> items = new ArrayList<>();
        for (ShoppingCarItem sci : sc.getItems()) {
            TradeItem item = new TradeItem();
            item.setBookId(sci.getBook().getId());
            item.setQuantity(sci.getQuantity());
            item.setTradeId(trade.getTradeId());
            items.add(item);
        }
        tradeItemDAO.batchSave(items);

        // 清空购物车
        sc.clear();
    }
}