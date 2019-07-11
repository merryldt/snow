package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.UserPermission;
import com.summer.icore.model.UserRolePermissionRelation;

import java.util.List;

public interface UserPermissionMapper extends BaseMapper<UserPermission> {

    List<UserPermission> findAuthorities();

    List<UserPermission> findAuthoritiesByRoleId(Integer roleId);

    List<UserPermission> findAuthoritiesByIds(List<Integer> authorityIds);

    void insertAuthorities(List<UserRolePermissionRelation> roleAuthorities);

    void updatePermissions(List<UserPermission> authorities);
}
