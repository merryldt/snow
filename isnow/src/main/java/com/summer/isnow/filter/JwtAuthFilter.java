package com.summer.isnow.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.summer.icommon.exception.GeneralException;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icore.model.UserAdmin;
import com.summer.icore.service.UserAdminService;
import com.summer.icore.serviceImpl.UserAdminServiceImpl;
import com.summer.isnow.shior.JWTToken;
import com.summer.icommon.utils.JwtUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class JwtAuthFilter extends AuthenticatingFilter {
	private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
	
    private static final int tokenRefreshInterval = 300;

    @Autowired
    protected UserAdminServiceImpl userAdminService;



//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        //对于OPTION请求做拦截，不做token校验
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
//            return false;
//        }
//
//        return super.preHandle(request, response);
//    }
    /**
     * 跨域设置
     */
//    @Override
//    protected void postHandle(ServletRequest request, ServletResponse response){
//        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
//        request.setAttribute("jwtShiroFilter.FILTERED", true);
//    }
    /**
     * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果是登录请求，直接跳过去,不进行token的验证,这里根据请求的接口地址来判断，在shiroConfig中添加
        // shiroFilter.setLoginUrl("/api/userAdmin/login");设置默认的登录接口,自己登录的接口，替换掉原来的login.jsp
        if(this.isLoginRequest(request, response))
        {return true;}
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
//            return false;
//        }
//        Boolean afterFiltered = (Boolean)(request.getAttribute("token"));
//        if( BooleanUtils.isTrue(afterFiltered))
//        {return true;}
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        }catch (IllegalStateException e){
            e.printStackTrace();
            throw new GeneralException(ResponseCode.LOGIN_OUT_OF_DATE,e);
        }catch (JWTDecodeException e){
            e.printStackTrace();
            throw new GeneralException("token格式错误");
        }catch (NullPointerException e){
//            e.printStackTrace();
            throw new GeneralException(ResponseCode.NOT_USER,e);
        } catch (Exception e) {
            throw new GeneralException(e);
        }
        return allowed || super.isPermissive(mappedValue);
    }
    /**
     *  66行  executeLogin(request, response);  执行成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
//        UserAdminServiceImpl userAdminService = new UserAdminServiceImpl();
        String newToken = null;
        if(token instanceof JWTToken){
            JWTToken jwtToken = (JWTToken)token;
            UserAdmin user = (UserAdmin) subject.getPrincipal();
            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
            if(shouldRefresh) {
                newToken = userAdminService.generateJwtToken(user.getUsername());
            }
        }
        if(StringUtils.isNotBlank(newToken))
        {httpResponse.setHeader("token", newToken);}

        return true;
    }

    /**
     * 66行  executeLogin(request, response);  执行失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        logger.error("JwtAuthFilter  Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }
    /**
     * 重写 66 行 executeLogin(request, response) 中创建token的方法
     * @param servletRequest
     * @param servletResponse
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getAuthzHeader(servletRequest);
        if(StringUtils.isNotBlank(jwtToken)&&!JwtUtils.isTokenExpired(jwtToken))
        {  return new JWTToken(jwtToken);}

        return null;
    }
    @Override
    protected boolean isPermissive(Object mappedValue) {
        if(mappedValue != null) {
            String[] values = (String[]) mappedValue;
            return Arrays.binarySearch(values, PERMISSIVE) >= 0;
        }
        return false;
    }
    /**
     * 对没有登录的请求拦截，返回json信息，覆盖掉shiro原本的跳转login.jsp的拦截方式
     * @param servletRequest
     * @param servletResponse
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        try {
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
            fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        }catch (Exception e){
            logger.error("JwtAuthFilter onAccessDenied 106 errorMsg:{}"+e);
        }
        return false;
    }



    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String header = httpRequest.getHeader("token");
        return StringUtils.removeStart(header, "Bearer");
    }

    protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
