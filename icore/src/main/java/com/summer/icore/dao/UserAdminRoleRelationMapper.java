package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.UserAdminRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminRoleRelationMapper extends BaseMapper<UserAdminRoleRelation> {
    String getUserAdminRoles(@Param("userId") int userId);
}
