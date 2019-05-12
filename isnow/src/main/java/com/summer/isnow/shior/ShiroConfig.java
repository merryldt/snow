package com.summer.isnow.shior;

import com.summer.icore.serviceImpl.UserServiceImpl;
import com.summer.isnow.filter.AnyRolesAuthorizationFilter;
import com.summer.isnow.filter.JwtAuthFilter;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(SecurityManager securityManager, UserServiceImpl userService) throws Exception{
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authc", createAuthFilter(userService));
        filterMap.put("anon", createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        filterRegistration.setFilter((Filter)factoryBean.getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
//        filterRegistration.setInitParameters(shiroFilterChainDefinition().getFilterChainMap());
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public Authenticator authenticator(UserServiceImpl userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
//        authenticator.setRealms(Arrays.asList( dbShiroRealm(userService)));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }


    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserServiceImpl userService) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
        return myShiroRealm;
    }

    @Bean
    public Realm dbShiroRealm(UserServiceImpl userService) {
        DbShiroRealm myShiroRealm = new DbShiroRealm(userService);
        return myShiroRealm;
    }


//    /**
//     * 设置过滤器
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService UserService) {
//    	ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//        factoryBean.setSecurityManager(securityManager);
//        Map<String, Filter> filterMap = factoryBean.getFilters();
//        filterMap.put("authc", createAuthFilter(UserService));
//        filterMap.put("anon", createRolesFilter());
//        factoryBean.setFilters(filterMap);
//        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
//
//        return factoryBean;
//    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/swagger-resources", "anon");
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("/swagger/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");

       chainDefinition.addPathDefinition("/api/logins/listUser", "anon");
        chainDefinition.addPathDefinition("/api/wxUser/**", "anon");
        chainDefinition.addPathDefinition("/statics/**", "anon");
        chainDefinition.addPathDefinition("/api/user/**", "anon");
        chainDefinition.addPathDefinition("/api/logins/login", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/logins/logout", "noSessionCreation,authc[permissive]");
        chainDefinition.addPathDefinition("/summere/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authc,anon[admin,manager]"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authc");
        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authc[permissive]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authc");
        return chainDefinition;
    }

    protected JwtAuthFilter createAuthFilter(UserServiceImpl userService){
        return new JwtAuthFilter(userService);
    }

    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }

}
