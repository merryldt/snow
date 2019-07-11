package com.summer.isnow.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.summer.icommon.exception.GeneralException;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/7/3 15:47
 */
@RestController
@Api(value = "filter错误处理", description = "filter错误处理")
public class ErrorController extends BasicErrorController {


    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        //自定义的错误信息类
        //status.value():错误代码，
        //body.get("message").toString()错误信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",status.value());
        jsonObject.put("msg", body.get("message").toString());
        //TokenException Filter抛出的自定义错误类
        if (!Strings.isNullOrEmpty((String) body.get("exception")) && body.get("exception").equals(GeneralException.class.getName())) {
            body.put("status", HttpStatus.FORBIDDEN.value());
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(jsonObject, status);
    }
    @Override
    protected ErrorProperties getErrorProperties() {
        return new ErrorProperties();
    }
    @Override
    public String getErrorPath() {
        return "error/error";
    }

}
