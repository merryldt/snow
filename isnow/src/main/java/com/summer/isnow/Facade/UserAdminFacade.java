package com.summer.isnow.Facade;

import com.summer.icommon.utils.JwtUtils;
import com.summer.icore.model.User;
import com.summer.icore.model.UserAdmin;
import com.summer.icore.service.UserAdminService;
import com.summer.icore.service.UserService;
import com.summer.isnow.dto.UserView;
import com.summer.isnow.exception.BaseException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class UserAdminFacade {

    private static final Logger logger = LoggerFactory.getLogger(UserAdminFacade.class);
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserAdminService userService;

    public Map<String,Object> login(String userName, String password){
        Map<String,Object> filters = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password, false);
//            token.setRememberMe(true);
            subject.login(token);
            subject.isAuthenticated();
//            subject.isRemembered();

            UserAdmin user = userService.getUserAdminByName(userName);
            String  authToken = userService.generateJwtToken(userName);
            filters.put("token",authToken);
            filters.put("tokenHead",tokenHead);
            filters.put("userId",user.getId());
            filters.put("userName",userName);
            filters.put("result",1);
        }catch (AuthenticationException e){
            filters.put("result",0);
            logger.error("login failed : {}", e);
        }catch (Exception e){
            logger.error("login failed : {}", e);
            throw new BaseException("用户密码错误");
        }
        return filters;
    }

    //通过姓名查找用户
    public UserAdmin getUserByName(Integer userId){
        UserAdmin user = userService.selectById(userId);
//        UserView userView = new UserView();
//        BeanUtils.copyProperties(user,userView);
        return user;
    }
}
