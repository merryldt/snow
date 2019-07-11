package com.summer.isnow.shior;

import com.summer.icommon.utils.JwtUtils;
import com.summer.icore.model.User;
import com.summer.icore.model.UserAdmin;
import com.summer.icore.serviceImpl.UserAdminServiceImpl;
import com.summer.icore.serviceImpl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTShiroRealm extends AuthorizingRealm {
    private final Logger log = LoggerFactory.getLogger(JWTShiroRealm.class);

    protected UserServiceImpl userService;
    @Autowired
    protected UserAdminServiceImpl userAdminService;

//    public JWTShiroRealm(UserServiceImpl userService){
//        this.userService = userService;
//        this.setCredentialsMatcher(new JWTCredentialsMatcher());
//    }
    public JWTShiroRealm(){
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();
        String username = JwtUtils.getUsername(token);
        System.out.println("suername ;====="+username);
        UserAdmin user = userAdminService.getJwtTokenInfo(username);
        if(user == null)
            throw new AuthenticationException("token过期，请重新登录");

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getSalt(), getName());

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println(principals + "进行授权操作");
        return new SimpleAuthorizationInfo();
    }
}