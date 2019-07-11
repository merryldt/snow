package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.UserAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminMapper extends BaseMapper<UserAdmin> {
    UserAdmin getUserAdminInfo(@Param("userName") String userName);
    List<UserAdmin> getUserAdminByRoleId(@Param("roleId") Integer roleId);
}
