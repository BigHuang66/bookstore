package com.huang.bookstore.dao;

import com.huang.bookstore.domain.Book;
import com.huang.bookstore.domain.ShoppingCarItem;
import com.huang.bookstore.web.CriteriaBook;
import com.huang.bookstore.web.Page;

import java.util.Collection;
import java.util.List;

public interface BookDAO {

    Book getBook(int id);

    Page<Book> getPage(CriteriaBook cb);

    long getTotalBookNumber(CriteriaBook cb);

    List<Book> getPageList(CriteriaBook cb, int pageSize);

    int getStoreNumber(Integer id);

    boolean batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCarItem> items);
}
