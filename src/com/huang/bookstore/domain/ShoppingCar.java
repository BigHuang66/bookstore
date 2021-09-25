package com.huang.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 购物车
 * @date 2021/9/22 22:34
 */
public class ShoppingCar {

    private HashMap<Integer, ShoppingCarItem> books = new HashMap<Integer, ShoppingCarItem>();

    public void updateItemQuantity(Integer id, int quantity) {
        ShoppingCarItem sci = books.get(id);
        if (sci != null) {
            sci.setQuantity(quantity);
        }
    }

    public void removeItem(Integer id) {
        books.remove(id);
    }

    public void clear() {
        books.clear();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public Collection<ShoppingCarItem> getItems() {
        return books.values();
    }

    public double getTotalMoney() {
        double total = 0;
        for (ShoppingCarItem sci : getItems()) {
            total += sci.getItemMoney();
        }
        return total;
    }

    public int getBookNumber() {
        int total = 0;
        for (ShoppingCarItem sci : getItems()) {
            total += sci.getQuantity();
        }
        return total;
    }

    public HashMap<Integer, ShoppingCarItem> getBooks() {
        return books;
    }

    public boolean hasBook(Integer id) {
        return books.containsKey(id);
    }

    public void addBook(Book book) {
        ShoppingCarItem sci = books.get(book.getId());
        if (sci == null) {
            sci = new ShoppingCarItem(book);
            books.put(book.getId(), sci);
        } else {
            sci.increment();
        }
    }
}
