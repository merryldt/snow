package com.summer.isnow.shior;

import com.summer.icore.model.User;
import com.summer.icore.model.UserAdmin;
import com.summer.icore.model.UserPermission;
import com.summer.icore.model.UserRole;
import com.summer.icore.serviceImpl.UserAdminServiceImpl;
import com.summer.icore.serviceImpl.UserPermissionServiceImpl;
import com.summer.icore.serviceImpl.UserRoleServiceImpl;
import com.summer.icore.serviceImpl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.relation.Role;
import java.util.*;

public class DbShiroRealm extends AuthorizingRealm {
    private final Logger log = LoggerFactory.getLogger(DbShiroRealm.class);

    private UserServiceImpl userService;

    @Autowired
    private UserAdminServiceImpl userAdminService;

    @Autowired
    private UserPermissionServiceImpl userPermissionService;
    @Autowired
    private UserRoleServiceImpl userRoleService;

    public DbShiroRealm() {
        //因为数据库中的密码做了散列，所以使用shiro的散列Matcher
        //      this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
       this.setCredentialsMatcher(
               new DBCredentialsMatcher(Md5Hash.ALGORITHM_NAME,1));
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    /**
     *
     * 认证信息.(身份验证)
     * Authentication 是用来验证用户身份
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        if(!supports(token)){
            String msg = "Realm does not support authentication token [" +
                    token + "].  Please ensure that the appropriate Realm implementation is " +
                    "configured correctly or that the realm accepts AuthenticationTokens of this type.";
            throw new AuthenticationException(msg);
        }
        System.out.println("suername ;====="+token);
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken)token;
        String username = userpasswordToken.getUsername();
        String password = new String(userpasswordToken.getPassword());
        UserAdmin user = userAdminService.getUserAdminInfo(username);
//        String result = new Md5Hash(password,user.getSalt(),3).toString();
        if(user == null){
            throw new AuthenticationException("用户名不存在");
        }
//        if(! result.equals(user.getPassword())){
//            throw new AuthenticationException("密码错误");
//        }
        //处理session
//        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
//        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
//        //获取当前已登录的用户session列表
//        SessionDAO sessionDAO = sessionManager.getSessionDAO();
//        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
//        UserAdmin temp;
//        for(Session session : sessions){
//            //清除该用户以前登录时保存的session，强制退出
//            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//            if (attribute == null) {
//                continue;
//            }
//
//            temp = (UserAdmin) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
//            if(username.equals(temp.getUsername())) {
//                sessionManager.getSessionDAO().delete(session);
//            }
//        }
        String name = getName();
        //将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断。第三个参数传入用户输入的用户名。
        //第二个参数放数据库中用户的密码
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),ByteSource.Util.bytes(user.getSalt()), getName());
    }
    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        System.out.println("-------------------------------------------------------------------------");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserAdmin user = (UserAdmin) principals.getPrimaryPrincipal();
        List<String> roles = user.getRoles();
        if(roles == null) {
            roles = userAdminService.getUserAdminRoles(user.getId());
            user.setRoles(roles);
        }
        List<UserPermission> userPermissionList = new ArrayList<>();
        if (roles != null){
            List<String> rolesName = new ArrayList<>();
            for(String s :roles){
                Integer roleId = Integer.parseInt(s);
                UserRole role = userRoleService.selectById(roleId);
                if(null != role){
                    rolesName.add(role.getCategory());
                }
                List<UserPermission> userPermissions = userPermissionService.findAuthoritiesByRoleId(roleId);
                userPermissionList.addAll(userPermissions);
            }
            simpleAuthorizationInfo.addRoles(rolesName);
        }
        if(null ==userPermissionList || userPermissionList.size()==0){
            return null;
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}