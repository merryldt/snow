package com.summer.icore.service;
import com.baomidou.mybatisplus.service.IService;
import com.summer.icore.model.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    List<UserRole>  findUserRoleByMap();
    UserRole findUserRoleByCategory(String category);
    UserRole  getRolesByPermission(Integer permissionId );
}
