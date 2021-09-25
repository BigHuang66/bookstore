package com.huang.bookstore.servlet;

import com.huang.bookstore.domain.User;
import com.huang.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author BigHuang
 * @version 1.0
 * @description: TODO
 * @date 2021/9/25 15:28
 */
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;

    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        String userName = req.getParameter("username");
        if (userName != null && !"".equals(userName)) {
            userName = new String(userName.getBytes(StandardCharsets.ISO_8859_1), "utf-8");
        }
        User user = userService.getUserWithTrades(userName);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/error/errpr-6.jsp");
            return;
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(req, resp);
    }
}
