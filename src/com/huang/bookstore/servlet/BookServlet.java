package com.huang.bookstore.servlet;

import com.google.gson.Gson;
import com.huang.bookstore.domain.*;
import com.huang.bookstore.service.AccountService;
import com.huang.bookstore.service.BookService;
import com.huang.bookstore.service.UserService;
import com.huang.bookstore.web.BookStoreWebUtils;
import com.huang.bookstore.web.CriteriaBook;
import com.huang.bookstore.web.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /***
     * @description: 输入框合法性验证（一级验证）
     * @return: java.lang.String
     */
    private String validateFormField(String username,String accountid) {
        StringBuilder errors = new StringBuilder("");
        if(username == null || username.trim().equals("")) {
            errors.append("用户名不能为空");
        }
        if(accountid == null || accountid.trim().equals("")) {
            errors.append("账号不能为空");
        }
        return errors.toString();
    }

    /***
     * @description: 用户名密码验证 （二级验证）
     * @return: java.lang.String
     */
    private String validateUser(String userName, String accountId) {
        boolean flag = false;
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            if (accountId.trim().equals(String.valueOf(user.getAccountId()))) {
                flag = true;
            }
        }
        StringBuilder errors = new StringBuilder("");
        if (!flag) {
            errors.append("用户账号密码不匹配");
        }
        return errors.toString();
    }

    /***
     * @description:  库存验证 （三级验证）
     * @param: request
     * @return: java.lang.String
     */
    private String validateBookStoreNumber(HttpServletRequest request) {
        ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
        StringBuilder errors = new StringBuilder("");
        // 循环 每本书都看一下库存是否足够
        for (ShoppingCarItem sci : sc.getItems()) {
            int quantity = sci.getQuantity();
            int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreAmount();
            if(quantity > storeNumber) {
                errors.append(sci.getBook().getTitle() + "库存不足<br>");
            }
        }
        return errors.toString();
    }

    /***
     * @description: 余额验证 （四级验证）
     * @return: java.lang.String
     */
    private String validateBalance(HttpServletRequest request, String accountId) {
        ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
        StringBuilder errors = new StringBuilder("");
        Account account = accountService.getAccount(Integer.parseInt(accountId));
        if (sc.getTotalMoney() > account.getBalance()) {
            errors.append("余额不足");
        }
        return errors.toString();
    }

    /***
     * @description: 多重验证，通过后调用数据库一系列刷新
     * @return: void
     */
    protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"),"utf-8");
        String accountid = request.getParameter("accountid");

        String errors = validateFormField(username, accountid);
        if (errors.equals("")) {
            errors = validateUser(username, accountid);
            if (errors.equals("")) {
                errors = validateBookStoreNumber(request);
                if (errors.equals("")) {
                    errors = validateBalance(request, accountid);
                    if (errors.equals("")) {
                        bookService.cash(BookStoreWebUtils.getShoppingCar(request), username, accountid);
                        request.setAttribute("username", username);
                        request.getRequestDispatcher("/WEB-INF/pages/Shoppingsuccess.jsp").forward(request, response);
                        return;
                    }
                }
            }
        }
        if(!errors.toString().equals("")) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("method");
        if(!"".equals(methodName) && methodName != null) {
            try {
                Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
                method.setAccessible(true);
                method.invoke(this, req, resp);
            } catch (Exception e) {
                // 这个异常继续往外抛 不抛如果拦下就不会经过Filter 就不会进行事务回滚
                throw new RuntimeException(e);
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "/error/error-3.jsp");
            return;
        }
    }

    /***
     * @description: 用于转发
     * @return: void
     */
    protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 需要去的页面
        String page = request.getParameter("page");
        if("cash".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
            return;
        }
        if("cart".equals(page)) {
            ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
            if(sc.isEmpty()) {
                request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
                return;
            }
            String idStr = request.getParameter("pageNo");
            int id = -1;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                System.out.println("来自BookServlet类的信息：浏览器中输入id有误");
            }
            request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp?pageNo=" + id).forward(request, response);
            return;
        }
        request.getRequestDispatcher("/error/error-4.jsp").forward(request, response);
        return;
    }

    /***
     * @description: 查看
     * @return: void
     */
    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page<Book> page = getPage(request);
        request.setAttribute("bookpage", page);
        request.setAttribute("date", getDate());
        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
        return;
    }

    /***
     * @description:
     * @return:
     */
    protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if(id == -1) {
            request.getRequestDispatcher("/error/error-1.jsp").forward(request, response);
            return;
        }
        ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
        bookService.removeItemFromShoppingCart(sc,id);
        if(sc.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        return;
    }

    /***
     * @description: 清空购物车
     * @return: void
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
        BookService.clearShoppingCar(sc);

        request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
        return;
    }

    /**
     * @description: 修改购物车中的物品的数量
     * @return: void
     */
    protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String quantityStr = request.getParameter("quantity");
        int id = -1;
        int quantity = -1;
        ShoppingCar sc = null;
        try {
            id = Integer.parseInt(idStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.out.println("来自BookServlet的信息：" + bookService.getBook(id) + "输入的值有误！");
        }

        if (id > 0 && quantity > 0) {
            sc = BookStoreWebUtils.getShoppingCar(request);
            bookService.updateItemQuantity(sc, id, quantity);
        }

        // 回传两个值 为了拿下所有的值 用 Object
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("bookNumber", sc.getBookNumber());
        result.put("totalMoney", sc.getTotalMoney());
        // 用 Gson 将 Java 对象转成 JavaScript 对象
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        // 改变传输的类型
        response.setContentType("text/javascript");
        // 这边传过去 JSP 页面直接用 'data' 接收就行了 还可以当作 Java 对象使用
        response.getWriter().print(jsonStr);
    }

    /**
     * @description: addToCar
     * @return: void
     */
    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = -1;
        boolean flag = false;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("来自BookServlet类的信息：浏览器中输入id有误");
        }
        if (id > 0) {
            ShoppingCar sc = BookStoreWebUtils.getShoppingCar(request);
            flag = bookService.addToCar(id, sc);
        }
        if (flag) {
            getBooks(request, response);
            return;
        }
        request.getRequestDispatcher("/error/error-1.jsp").forward(request, response);
        return;
    }

    /***
     * @description: 返回一本书的信息
     * @return: void
     */
    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = -1;
        Book book = null;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {}
        if(id > 0) {
            book = bookService.getBook(id);
        }
        if(book == null) {
            request.getRequestDispatcher("/error/error-1.jsp").forward(request, response);
            return;
        }
        Page<Book> page = getPage(request);
        List<Book> books = page.getList();
        boolean flag = false;
        // 判断是否包含 这本书 需要重写 equals方法
        if(books.contains(book)) {
            flag = true;
        }
        if(!flag) {
            request.getRequestDispatcher("/error/error-2.jsp").forward(request, response);
            return;
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
    }

    /**
     * @description:  返回显示的页面
     * @param: request
     * @return: com.huang.bookstore.web.Page<com.huang.bookstore.domain.Book>
     */
    private Page<Book> getPage(HttpServletRequest request) {
        String pageNoStr = request.getParameter("pageNo");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;
        // 三个分开写是为了一个转换出错 其他的还能继续运行
        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException e) {}
        try {
            minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException e) {}
        try {
            maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException e) {}
        // 创建这本书 能显示的内容也在这里面
        CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
        return bookService.getPage(cb);
    }

    /**
     * @description:
     * @return:
     */
    private int getId(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        int id = -1;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (id > 0 && bookService.getBook(id) != null) {
            return id;
        }
        return -1;
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("现在是yyyy年MM月dd日 HH时mm分ss");
        return sdf.format(new Date());
    }


}
