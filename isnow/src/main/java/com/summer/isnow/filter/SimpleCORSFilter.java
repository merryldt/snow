package com.summer.isnow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Integer.MAX_VALUE)
public class SimpleCORSFilter implements Filter {

  private Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request  = (HttpServletRequest) req;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,x-requested-with");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Accept-Ranges", "byte");

//    response.setHeader("Access-Control-Allow-Origin", "*");
//    response.setHeader("Access-Control-Allow-Credentials", "true");
//    response.setHeader("Access-Control-Allow-Methods", "*");
//    response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,x-requested-with");
//    response.setHeader("Access-Control-Expose-Headers", "*");

    String method= request.getMethod();

    if (method.equals("OPTIONS")){
      response.setStatus(200);
    }
    logger.debug("{} filter procced", "SimpleCORSFilter", ((HttpServletRequest)req).getPathInfo());
    chain.doFilter(req, res);
  }

  public void init(FilterConfig filterConfig) {
  }

  public void destroy() {
  }
}