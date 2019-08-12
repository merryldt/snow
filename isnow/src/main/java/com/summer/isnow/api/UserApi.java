package com.summer.isnow.api;

import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icommon.utils.StringUtil;
import com.summer.icore.model.User;
import com.summer.isnow.Facade.UserFacade;
import com.summer.isnow.dto.UserView;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("sys/user")
@RestController
public class UserApi {

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);
    @Autowired
    private UserFacade userFacade;

    //后台登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseModel  login(@RequestBody UserView userView){
        if(!StringUtil.isNull(userView.getUserName())|| !StringUtil.isNull(userView.getPassword())){
            logger.warn("userName or password is null");
            return new ResponseModel(ResponseCode.REQUEST_PARAMETER_NULL);
        }
         Map<String,Object> map = userFacade.login(userView.getUserName(),userView.getPassword());
         if(map.containsKey("result")){
             if((Integer)map.get("result")==1){
                 return new ResponseModel(ResponseCode.OK,map);
             }
         }
         return new ResponseModel(ResponseCode.LOGIN_ERROR);
    }
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseModel getAdminInfo() {

        Subject subject = SecurityUtils.getSubject();
        String username = "";
        //已经登录过
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            username = user.getUserName();
        }
        UserView userView = userFacade.getUserByName(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", userView.getUserName());
        data.put("roles", new String[]{"TEST"});
        data.put("icon", userView.getIcon());
        return new ResponseModel(ResponseCode.OK,data);
    }

    //显示用户信息
    @ApiOperation(value = "用户列表信息")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ResponseModel listUser(){
//        Page<?> pages = PageHelper.startPage(userView.getPage(),userView.getRows());
        List<UserView> userList = userFacade.listUser();
        return new ResponseModel(ResponseCode.SUCCESS,userList);
    }

}
