package com.summer.isnow.api;

import com.summer.icommon.utils.JwtUtils;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icommon.utils.StringUtils;
import com.summer.icore.model.User;
import com.summer.icore.model.UserAdmin;
import com.summer.isnow.Facade.UserAdminFacade;
import com.summer.isnow.Facade.UserFacade;
import com.summer.isnow.dto.UserView;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * @author liudongting
 * @date 2019/6/28 14:59
 */
@RequestMapping("api/userAdmin")
@RestController
public class UserAdminApi extends  BaseApi{

    private static final Logger logger = LoggerFactory.getLogger(UserAdminApi.class);
    @Autowired
    private UserAdminFacade userFacade;

    /**
     * //后台登陆
     * @param userView
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseModel  login(@RequestBody UserAdmin userView){
        if(!StringUtils.isNull(userView.getUsername())|| !StringUtils.isNull(userView.getPassword())){
            logger.warn("userName or password is null");
            return new ResponseModel(ResponseCode.REQUEST_PARAMETER_NULL);
        }
         Map<String,Object> map = userFacade.login(userView.getUsername(),userView.getPassword());
         User user = new User();
         map.put("user",user);
         if(map.containsKey("result")){
             if((Integer)map.get("result")==1){
                 return new ResponseModel(ResponseCode.SUCCESS,map);
             }
         }
         return new ResponseModel(ResponseCode.LOGIN_ERROR);
    }
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResponseModel getAdminInfo(HttpServletRequest httpRequest,@RequestBody UserAdmin userViews) {
        logger.warn("userNam111e or password is null");
        Subject subject = SecurityUtils.getSubject();
        String username = "";
        //已经登录过
        if (subject.isAuthenticated()) {
            UserAdmin user = (UserAdmin) subject.getPrincipal();
            if(null!= user){
                if(! user.getUsername().equals(userViews.getUsername())){

                }
            }
            username = user.getUsername();
        }
        String header = httpRequest.getHeader("token");
        String name = JwtUtils.getUsername(header);
        System.out.println("name"+name);
        UserAdmin userView = userFacade.getUserByName(userViews.getId());
        if(null == userView){
            return new ResponseModel(ResponseCode.SUCCESS,"查无此人");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userView.getId());
        data.put("userName", username);
        data.put("roles", new String[]{"admin"});
        data.put("icon", userView.getIcon());
        data.put("user", userView);
        return new ResponseModel(ResponseCode.SUCCESS,data);
    }
    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseModel logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        logger.info("msg","安全退出！");
        return new ResponseModel(ResponseCode.SUCCESS);
    }


}
