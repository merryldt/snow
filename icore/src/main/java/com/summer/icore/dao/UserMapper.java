package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserInfo(@Param("userName") String userName);
}
