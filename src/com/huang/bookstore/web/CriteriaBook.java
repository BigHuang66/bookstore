package com.huang.bookstore.web;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 封装查询条件
 * @date 2021/9/22 21:47
 */
public class CriteriaBook {

    private int minPrice = 0;
    private int maxPrice = Integer.MAX_VALUE;

    /** 当前页页码 */
    private int pageNo;

    public CriteriaBook() {
    }

    public CriteriaBook(int minPrice, int maxPrice, int pageNo) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.pageNo = pageNo;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "CriteriaBook{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", pageNo=" + pageNo +
                '}';
    }
}
