package com.cskaoyan.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MyServlet",urlPatterns = "/aaa/bbb/ccc")
public class MyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        /*
        * 路径：以/开始  ： /表示web根目录  WEBROOT
        *
        * 不以/开始:
        * */
       // request.getRequestDispatcher("/admin/index.jsp").forward(request,response);


        response.setHeader("refresh","2;url=/result.jsp");

        //

        request.getServletContext().getRealPath("/WEB-INF/config.xml")
    }
}
