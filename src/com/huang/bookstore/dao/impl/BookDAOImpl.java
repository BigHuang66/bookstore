package com.huang.bookstore.dao.impl;

import com.huang.bookstore.dao.BookDAO;
import com.huang.bookstore.dao.Dao;
import com.huang.bookstore.domain.Book;
import com.huang.bookstore.domain.ShoppingCarItem;
import com.huang.bookstore.web.CriteriaBook;
import com.huang.bookstore.web.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 11:08
 */
public class BookDAOImpl extends Dao<Book> implements BookDAO {

    @Override
    public Book getBook(int id) {
        String sql = "select id, author, title, price, publishDate, salesAmount, storeAmount, remark from mybooks where id = ?";;
        return get(sql, id);
    }

    /***
     * @description:  返回当前页应该显示的内容
     * @param: cb
     * @return: com.huang.bookstore.web.Page<com.huang.bookstore.domain.Book>
     */
    @Override
    public Page<Book> getPage(CriteriaBook cb) {
        Page page = new Page<>(cb.getPageNo());
        page.setTotalItemNumber(getTotalBookNumber(cb));
        // 把能显示的页数赋值给它 及时更新它
        cb.setPageNo(page.getPageNo());
        // cb这个对象 按照每页 4本书来显示
        page.setList(getPageList(cb, 4));
        return page;
    }

    @Override
    public long getTotalBookNumber(CriteriaBook cb) {
        String sql = "select count(id) from mybooks where price >= ? and price <= ?";
        return getForValue(sql, cb.getMinPrice(), cb.getMaxPrice());
    }

    /***
     * @description: 对当前页进行条件查询
     * @return: java.util.List<com.huang.bookstore.domain.Book>
     */
    @Override
    public List<Book> getPageList(CriteriaBook cb, int pageSize) {
        String sql = "select id, author, title, price, publishDate, salesAmount, storeAmount, remark from mybooks where price >= ? and price <= ? limit ?, ?";
        // (cb.getPageNo() - 1) * pageSize： 算出页数
        // pageSize： 每页 pageSize 个
        int No = 0;
        if(cb.getPageNo() != 0) {
            No = (cb.getPageNo() - 1) * pageSize;
        }
        return getForList(sql, cb.getMinPrice(), cb.getMaxPrice(), No, pageSize);
    }

    @Override
    public int getStoreNumber(Integer id) {
        String sql = "select storeAmount from mybooks where id =?";
        return getForValue(sql, id);
    }

    @Override
    public boolean batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCarItem> items) {
        String sql = "update mybooks set salesAmount = salesAmount + ?, storeAmount = storeAmount - ? where id = ?";
        Object[][] params = null;
        // 一共有 size 条记录 一个记录需要三个变量
        params = new Object[items.size()][3];
        // 因为 Collection 无法用 get去获取第几个值 所以把他转成 ArrayList
        ArrayList<ShoppingCarItem> scis = new ArrayList<>(items);
        for(int i = 0;i < items.size();i++) {
            // SQL 里面前面两个都是钱 第三个是书本的 ID  都从购物车里面获取
            params[i][0] = scis.get(i).getQuantity();
            params[i][1] = scis.get(i).getQuantity();
            params[i][2] = scis.get(i).getBook().getId();
        }
        int[] batch = batch(sql, params);
        for (int i : batch) {
            if(i != 1) {
                return false;
            }
        }
        return true;
    }
}
