package io.github.nprcz.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class LoggerFilter implements Filter{
  private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //we secure the filter in case someone wants to use it
        //if our req is a HttpServeletReq then
        if (request instanceof HttpServletRequest){
        var httpReq = (HttpServletRequest) request;//casting to HttpServReq
        logger.info("[doFilter] " + httpReq.getMethod()+ " " +httpReq.getRequestURI()); //loging filter name,method name and Uri info
    }
      //you have to add it otherwise the req will not go further
        chain.doFilter(request,response);
    }



}
