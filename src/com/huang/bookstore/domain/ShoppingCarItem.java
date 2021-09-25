package com.huang.bookstore.domain;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 购物车选项
 * @date 2021/9/22 22:30
 */
public class ShoppingCarItem {

    private Book book;

    private int quantity;

    public ShoppingCarItem() {
    }

    public ShoppingCarItem(Book book) {
        this.book = book;
        this.quantity = 1;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increment() {
        this.quantity ++;
    }

    public double getItemMoney() {
        return Double.parseDouble(this.book.getPrice()) * this.quantity;
    }
}
