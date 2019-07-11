package com.summer.icore.service;
import com.baomidou.mybatisplus.service.IService;
import com.summer.icore.model.UserPermission;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.List;
import java.util.Map;

public interface UserPermissionService extends IService<UserPermission> {
    Map<String, String> loadFilterChainDefinitions();
    void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Integer roleId);
    List<UserPermission> findAuthoritiesByRoleId(Integer roleId);
}
