//package com.summer.isnow.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.base.Strings;
//import com.summer.icommon.exception.GeneralException;
//import io.swagger.annotations.Api;
//import org.springframework.boot.autoconfigure.web.*;
//import org.springframework.boot.autoconfigure.web.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
///**
// * @author liudongting
// * @date 2019/7/3 17:11
// */
//@RestController
//@Api(value = "filter错误处理", description = "filter错误处理")
//public class MyBasicErrorController extends AbstractErrorController {
//
//    public MyBasicErrorController(ErrorAttributes errorAttributes) {
//        super(errorAttributes);
//    }
//    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request,Throwable e) {
//        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
//        HttpStatus status = getStatus(request);
//        //自定义的错误信息类
//        //status.value():错误代码，
//        //body.get("message").toString()错误信息
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code",status.value());
//        jsonObject.put("msg", body.get("message").toString());
//       boolean flag = getTraceParameter(request);
//        System.out.println("flag"+flag);
//        //TokenException Filter抛出的自定义错误类
//        if (!Strings.isNullOrEmpty((String) body.get("exception")) && body.get("exception").equals(GeneralException.class.getName())) {
//            body.put("status", HttpStatus.FORBIDDEN.value());
//            status = HttpStatus.FORBIDDEN;
//        }
//        return new ResponseEntity<>(jsonObject, status);
//    }
//    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
//        ErrorProperties.IncludeStacktrace include = this.getErrorProperties().getIncludeStacktrace();
//        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
//            return true;
//        } else {
//            return include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM ? this.getTraceParameter(request) : false;
//        }
//    }
//    protected ErrorProperties getErrorProperties() {
//        return new ErrorProperties();
//    }
//    @Override
//    protected boolean getTraceParameter(HttpServletRequest request) {
//        String parameter = request.getParameter("trace");
//        if (parameter == null) {
//            return false;
//        } else {
//            return !"false".equals(parameter.toLowerCase());
//        }
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "error/error";
//    }
//}
