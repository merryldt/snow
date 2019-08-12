package com.summer.isnow.handler;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.summer.icommon.exception.GeneralException;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icommon.utils.StringUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.io.Writer;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

/**
 * spring mvc 从容器中获取了处理异常的HandlerExceptionResolver，spring mvc提供三种异常处理器：
 * DefaultHandlerExceptionResolver， ResponseStatusExceptionResolver， ExceptionHandlerExceptionResolver
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
        // 异常处理逻辑 goes here
        logger.error("got exception: ", e);
        Writer out = null;
        try {
            resp.setCharacterEncoding(CharsetUtil.UTF_8);
            resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            out = resp.getWriter();
            //判断是不是JsonResult
            out.write(JSON.toJSONString(wrapper(e,o)));
        }catch (IOException ex){
            logger.warn("输出异常",ex);
        }finally {
            if(null != out){
                try {
                     out.close();
                }catch (IOException eo){
                    eo.printStackTrace();
                }
            }
        }
        return new ModelAndView();

    }
    private ResponseModel wrapper(Exception e, Object o){
        ResponseModel result = null;
        if(e instanceof GeneralException){
            GeneralException b = (GeneralException) e;
//            int code = b.getCode();
            String message = null;
            if(StringUtil.isNotBlank(b.getMessage())){
                message = b.getMessage();
            }else{
                message = "系统繁忙，请稍后重试";
            }
            result =ResponseModel.ERROR(message);
            logger.warn(StringUtil.defaultIfBlank(b.getMessage(), StringUtil.defaultIfBlank(e.getMessage(), "系统异常")), e);
        }else if(e instanceof IllegalArgumentException) {
            result = ResponseModel.ERROR("参数错误");
        }else if(e instanceof HttpRequestMethodNotSupportedException){
            result = ResponseModel.ERROR("不支持此种请求方式");
        } else  if (e instanceof IllegalStateException) {
            result = ResponseModel.ERROR("token错误IllegalStateException");
        }else  if (e instanceof UnauthenticatedException) {
            result = ResponseModel.ERROR("token错误");
        } else if (e instanceof UnauthorizedException) {
            result = ResponseModel.ERROR("用户无权限");
        } else if(e instanceof MethodArgumentNotValidException){
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            if(bindingResult==null){
                result = ResponseModel.ERROR("请按照接口参数传递参数");
                return result;
            }
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if(fieldErrors!=null && fieldErrors.size()>0){
                if(StringUtil.isNotBlank(fieldErrors.get(0).getDefaultMessage())){
                    result = ResponseModel.ERROR(fieldErrors.get(0).getDefaultMessage());
                    return result;
                }
                result = ResponseModel.ERROR("请按照接口参数传递参数");
                return result;
            }
            result = ResponseModel.ERROR("请按照接口参数传递参数");
            return result;
        }else {
            result = ResponseModel.ERROR("系统繁忙，请稍后重试");
            logger.error(StringUtil.defaultIfBlank(e.getMessage(), "系统异常"), e);
        }

        return result;
    }
}
