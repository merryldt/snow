/*
package com.summer.isnow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Order(1)
public class SimpleCORSFilter implements Filter {

      private Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);
      @Override
      public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
          throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request  = (HttpServletRequest) req;
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,x-requested-with,Authorization,token");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Accept-Ranges", "byte");

            String method= request.getMethod();
            if (method.equals("OPTIONS")){
              response.setStatus(200);
            }
            logger.debug("{} filter procced", "SimpleCORSFilter", ((HttpServletRequest)req).getPathInfo());
            chain.doFilter(req, res);
      }
      private List<String> urlList;
      @Override
      public void init(FilterConfig filterConfig) {
      }

      @Override
      public void destroy() {
      }
}*/
