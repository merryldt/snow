package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {
     UserRole  getRolesByPermission(@Param("permissionId") Integer permissionId );
}
