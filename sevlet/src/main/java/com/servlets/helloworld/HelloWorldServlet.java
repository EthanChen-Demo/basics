package com.servlets.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ��ʼ��
    public void init() throws ServletException {
        System.out.println("����init()�������������г�ʼ������");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("����doGet()��������������GET����");
        response.setContentType("text/html;charset=GB2312");
        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<BODY>");
        out.println("����Servlet������");
        out.println("</BODY>");
        out.println("</HTML>");
    }

    // ����http POST����
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // ����ʵ��
    public void destroy() {
        super.destroy();
        System.out.println("����destroy()������������������ʵ���Ĺ���");
    }
}
