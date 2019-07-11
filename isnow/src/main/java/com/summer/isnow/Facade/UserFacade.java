package com.summer.isnow.Facade;

import com.summer.icore.model.User;
import com.summer.icore.service.UserService;
import com.summer.isnow.dto.UserView;
import com.summer.isnow.exception.BaseException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class UserFacade {

    private static final Logger logger = LoggerFactory.getLogger(UserFacade.class);
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService userService;

    public Map<String,Object> login(String userName, String password){
        Map<String,Object> filters = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password, false);
            token.setRememberMe(true);
            subject.login(token);
            subject.isAuthenticated();
            subject.isRemembered();
            String  authToken = userService.generateJwtToken(userName);
            filters.put("token",authToken);
            filters.put("tokenHead",tokenHead);
            filters.put("result",1);
        }catch (AuthenticationException e){
            filters.put("result",0);
            logger.error("login failed : {}", e);
        }catch (Exception e){
            throw new BaseException("用户密码");
        }
        return filters;
    }

    //通过姓名查找用户
    public UserView getUserByName(String userName){
        User user = userService.getUserByName(userName);
        UserView userView = new UserView();
        BeanUtils.copyProperties(user,userView);
        return userView;
    }

    //显示用户列表
    public List<UserView> listUser(){
        Map<String,Object> filters = new HashMap<>();
        List<UserView> userViewList = new ArrayList<>();
        List<User> userList = userService.selectByMap(filters);
        for(User user : userList){
             UserView userView = new UserView();
             userView.setUserName(user.getUserName());
             userView.setIcon(user.getIcon());
             userViewList.add(userView);
        }
        return userViewList;
    }
}
