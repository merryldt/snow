package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.icore.dao.UserAdminMapper;
import com.summer.icore.model.UserAdmin;
import com.summer.icore.utils.ShiroUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserPermission;
import  com.summer.icore.service.UserPermissionService;
import  com.summer.icore.dao.UserPermissionMapper;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper,UserPermission> implements UserPermissionService{

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private UserAdminMapper userAdminMapper;


    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        List<UserPermission>  userPermissions = userPermissionMapper.findAuthorities();
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        if (userPermissions.size() > 0) {
            String uris;
            String[] uriArr;
            for (UserPermission userPermission : userPermissions) {
                if (StringUtils.isEmpty(userPermission.getCategory())) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("roles").append("[").append(userPermission.getCategory()).append("]");
                uris = userPermission.getUri();
                if(null != uris){
                    uriArr = uris.split(",");
                    for (String uri : uriArr) {
                        filterChainDefinitionMap.put(uri, sb+"");
                    }
                }
            }
        }
        filterChainDefinitionMap.put("/user/login", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/user/logout", "anon");
        //拦截所有请求
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    /**
     * 在对角色进行增删改操作时，需要调用此方法进行动态刷新
     * @param shiroFilterFactoryBean
     */
    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Integer roleId) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            List<UserAdmin> users = userAdminMapper.getUserAdminByRoleId(roleId);

            if (users.size() == 0) {
                return;
            }
            for (UserAdmin user : users) {
                ShiroUtil.kickOutUser(user.getUsername(), false);
            }
        }
    }

    @Override
    public List<UserPermission> findAuthoritiesByRoleId(Integer roleId) {
        return userPermissionMapper.findAuthoritiesByRoleId(roleId);
    }
}
