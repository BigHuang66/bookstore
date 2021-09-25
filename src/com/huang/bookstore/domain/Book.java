package com.huang.bookstore.domain;

import java.sql.Date;
import java.util.Objects;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/22 21:47
 */
public class Book {

    private int id;

    private String author;

    private String title;

    private String price;

    private Date publishDate;

    private int salesAmount;

    private int storeAmount;

    private String remark;

    public Book() {
    }

    public Book(int id, String author, String title, String price, Date publishDate, int salesAmount, int storeAmount, String remark) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.publishDate = publishDate;
        this.salesAmount = salesAmount;
        this.storeAmount = storeAmount;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(int salesAmount) {
        this.salesAmount = salesAmount;
    }

    public int getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(int storeAmount) {
        this.storeAmount = storeAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", publishDate=" + publishDate +
                ", salesAmount=" + salesAmount +
                ", storeAmount=" + storeAmount +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && salesAmount == book.salesAmount && storeAmount == book.storeAmount && Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(price, book.price) && Objects.equals(publishDate, book.publishDate) && Objects.equals(remark, book.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, price, publishDate, salesAmount, storeAmount, remark);
    }
}
