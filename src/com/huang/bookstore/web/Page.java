package com.huang.bookstore.web;

import java.util.List;

/**
 * @author BigHuang
 * @version 1.0
 * @description: 封装翻页信息的 Page 类
 * @date 2021/9/22 21:47
 */
public class Page<T> {
    // 当前第几页
    private int pageNo;

    // 当前页的List
    private List<T> list;

    // 每页显示多少条记录
    private int pageSize = 3;

    // 共有多少条记录
    private long totalItemNumber;

    public Page(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > getTotalPageNumber()) {
            pageNo = getTotalPageNumber();
        }
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalPageNumber() {
        int totalPageNumber = (int) totalItemNumber / pageSize;
        if (totalItemNumber % pageSize != 0) {
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public void setTotalItemNumber(long totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
    }

    boolean isHasNext() {
        if (getPageNo() < getTotalPageNumber()) {
            return true;
        }
        return false;
    }

    boolean isHasPrev() {
        if (getPageNo() > 1) {
            return true;
        }
        return false;
    }

    int getPrePage() {
        if (isHasPrev()) {
            return getPageNo() - 1;
        }
        return getPageNo();
    }

    int getNextPage() {
        if (isHasNext()) {
            return getPageNo() + 1;
        }
        return getPageNo();
    }
}
