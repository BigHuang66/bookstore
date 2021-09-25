package com.huang.bookstore.domain;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/22 22:58
 */
public class TradeItem {

    private int itemId;

    private int bookId;

    private int quantity;

    private int tradeId;

    private Book book;

    public TradeItem() {
    }

    public TradeItem(int bookId, int quantity, int tradeId) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.tradeId = tradeId;
    }

    public TradeItem(int itemId, int bookId, int quantity, int tradeId) {
        this.itemId = itemId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.tradeId = tradeId;
    }

    public TradeItem(int itemId, int bookId, int quantity, int tradeId, Book book) {
        this.itemId = itemId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.tradeId = tradeId;
        this.book = book;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "TradeItem{" +
                "itemId=" + itemId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", tradeId=" + tradeId +
                ", book=" + book +
                '}';
    }
}
