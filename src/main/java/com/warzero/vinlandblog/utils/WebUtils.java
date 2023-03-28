package com.warzero.vinlandblog.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtils {

    public static String renderString(HttpServletResponse servletResponse, String text){
        try {
            servletResponse.setStatus(200);
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("utf-8");
            servletResponse.getWriter().print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
