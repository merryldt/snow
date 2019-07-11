package com.summer.isnow.shior;


import com.summer.icore.service.UserPermissionService;
import com.summer.isnow.filter.AnyRolesAuthorizationFilter;
import com.summer.isnow.filter.JwtAuthFilter;
import com.summer.isnow.handler.MyExceptionHandler;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;
import java.util.*;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    private static final String CACHE_KEY = "shiro:cache:";
    private static final String SESSION_KEY = "shiro:session:";
    private static final String NAME = "custom.name";
    private static final String VALUE = "/";


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserPermissionService userService) throws Exception{

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String,Filter> filterMap = new LinkedHashMap<>(3);
//        filterMap.put("roles",rolesAuthorizationFilter());
        shiroFilter.setLoginUrl("/api/userAdmin/login");
        filterMap.put("authc", createAuthFilter());
        filterMap.put("anon", createRolesFilter());
//        filterMap.put("anyRole", createRolesFilter());

        shiroFilter.setFilters(filterMap);
        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        shiroFilter.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
//        shiroFilter.setFilterChainDefinitionMap(userService.loadFilterChainDefinitions());

        return  shiroFilter;
    }
    @Bean("securityManager")
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setSessionManager(sessionManager);
//        manager.setCacheManager(redisCacheManager);
//        manager.setRealm(DbRealm());
        manager.setRealms(Arrays.asList(jwtShiroRealm(),DbRealm()));
        return manager;
    }
    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(),DbRealm()));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }


    @Bean("defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 生命周期
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return new LifecycleBeanPostProcessor();
    }

    @Bean("delegatingFilterProxy")
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean("dbRealm")
    public Realm DbRealm() {
        DbShiroRealm realm = new DbShiroRealm();
//        realm.setCacheManager(redisCacheManager);
//        realm.setAuthenticationCachingEnabled(false);
//        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }
    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm() {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm();
        return myShiroRealm;
    }
    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");

        //加上shior后需要添加
        chainDefinition.addPathDefinition("/configuration/security", "anon");
        chainDefinition.addPathDefinition("/configuration/ui", "anon");
        chainDefinition.addPathDefinition("/docs/**", "anon");
        chainDefinition.addPathDefinition("/images/**", "anon");


        chainDefinition.addPathDefinition("/statics/**", "anon");
        //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/api/userAdmin/login", "anon");
        chainDefinition.addPathDefinition("/api/userAdmin/logout", "anon");
        chainDefinition.addPathDefinition("/api/userAdmin/info", "noSessionCreation,authc,anon[Commodity_ype]");
        chainDefinition.addPathDefinition("/sys/user/**", "noSessionCreation,authc,anon[Commodity_ype]");
        chainDefinition.addPathDefinition("/sys/role/**", "noSessionCreation,authc,anon[Commodity_ype]");
//        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authc,anon[admin,manager]"); //只允许admin或manager角色的用户访问
//        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authc");
//        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
//        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authc[permissive]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authc");
        return chainDefinition;
    }
//    ????
    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    protected JwtAuthFilter createAuthFilter(){
        return new JwtAuthFilter();
    }

    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }
//
    /**
     * 注册全局异常处理
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {

        return new MyExceptionHandler();
    }

    /**
     * 使用session
     */

    /**
     * Redis集群使用RedisClusterManager，单个Redis使用RedisManager
     * @param redisProperties
     * @return
     */
//
//    @Bean
//    public RedisManager redisManager(JedisPool jedisPool) {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setJedisPool(jedisPool);
//        return redisManager;
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setKeyPrefix(CACHE_KEY);
//        redisCacheManager.setRedisManager(redisManager);
//        return redisCacheManager;
//    }
//
//    @Bean
//    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setKeyPrefix(SESSION_KEY);
//        redisSessionDAO.setRedisManager(redisManager);
//        return redisSessionDAO;
//    }
//    @Bean
//    public DefaultWebSessionManager sessionManager(RedisSessionDAO sessionDAO, SimpleCookie simpleCookie) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(sessionDAO);
//        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setSessionIdCookie(simpleCookie);
//        return sessionManager;
//    }
//
//    @Bean
//    public SimpleCookie simpleCookie() {
//        SimpleCookie simpleCookie = new SimpleCookie();
//        simpleCookie.setName(NAME);
//        simpleCookie.setValue(VALUE);
//        return simpleCookie;
//    }

}
