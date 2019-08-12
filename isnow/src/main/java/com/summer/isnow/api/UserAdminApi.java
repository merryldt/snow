package com.summer.isnow.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.summer.icommon.utils.*;
import com.summer.icore.model.User;
import com.summer.icore.model.UserAdmin;
import com.summer.isnow.Facade.UserAdminFacade;
import com.summer.isnow.dto.UserAdminView;
import io.swagger.annotations.*;
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
@Api(value = "后台用户类", tags = "后台用户操作接口", description = "对管理用户")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "请求正常完成"),
        @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
        @ApiResponse(code = 500, message = "服务器出现异常")}
)
@RequestMapping("api/userAdmin")
@RestController
public class UserAdminApi extends  BaseApi{

    private static final Logger logger = LoggerFactory.getLogger(UserAdminApi.class);
    @Autowired
    private UserAdminFacade userFacade;

    private static String key="shiro:cache:com.summer.isnow.shior.JWTShiroRealm.authorizationCache:";

    /**
     * //后台登陆
     * @param userView
     * @return
     */
    @ApiOperation(value = "后台登录接口", notes = "后台实现登录", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseModel  login(@RequestBody UserAdminView userView){
        if(!StringUtil.isNull(userView.getUsername())|| !StringUtil.isNull(userView.getPassword())){
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
    @JsonView(value = View.Base.class )
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)

    public ResponseModel getAdminInfo(HttpServletRequest httpRequest,@RequestBody UserAdmin userViews) {
        logger.warn("userNam111e or password is null");
        String username = "";
        //已经登录过
        String header = httpRequest.getHeader("token");
        String name = JwtUtils.getUsername(header);
        UserAdminView userView = userFacade.getUserByName(userViews.getId());
        if(null == userView){
            return new ResponseModel(ResponseCode.SUCCESS,"查无此人");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userView.getId());
        data.put("userName", userView.getUsername());
        data.put("roles", new String[]{"admin"});
        data.put("icon", userView.getIcon());
        data.put("user", userView);
        return new ResponseModel(ResponseCode.OK,data);
    }
    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseModel logout(HttpServletRequest httpRequest){
        Subject subject = SecurityUtils.getSubject();
//        Jedis jedis = RedisConfig.getJedis();
//        String header = httpRequest.getHeader("token");
//        header= org.apache.commons.lang3.StringUtil.removeStart(header, "Bearer");
//        String username = JwtUtils.getUsername(header);
//        String keys = key+username;
//         jedis.del(keys);
        subject.logout();
        logger.info("msg","安全退出！");
        return new ResponseModel(ResponseCode.SUCCESS);
    }


}
