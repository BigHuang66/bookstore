package com.huang.bookstore.web;

import com.huang.bookstore.domain.ShoppingCar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 10:08
 */
public class BookStoreWebUtils {
    public static ShoppingCar getShoppingCar(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ShoppingCar sc = (ShoppingCar) session.getAttribute("ShoppingCar");
        if (sc == null) {
            sc = new ShoppingCar();
            session.setAttribute("ShoppingCat", sc);
        }
        return sc;
    }
}
