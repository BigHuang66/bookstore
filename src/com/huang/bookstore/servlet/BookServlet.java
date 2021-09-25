package com.huang.bookstore.servlet;

import com.huang.bookstore.service.AccountService;
import com.huang.bookstore.service.BookService;
import com.huang.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/25 15:39
 */
public class BookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BookService bookService = new BookService();

    private UserService userService = new UserService();

    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
