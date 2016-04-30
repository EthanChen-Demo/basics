package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
    private String charsetName; // UTF-8
    private boolean isForceToEncode;

    public void init(FilterConfig filterConfig) throws ServletException {
        charsetName = filterConfig.getInitParameter("charsetName");
        isForceToEncode = Boolean.valueOf(filterConfig.getInitParameter("isForceToEncode")); 
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isForceToEncode) {
            if (charsetName != null && !"".equals(charsetName)) {
                request.setCharacterEncoding(charsetName);
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
