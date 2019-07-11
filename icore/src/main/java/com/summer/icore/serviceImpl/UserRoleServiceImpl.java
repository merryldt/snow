package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserRole;
import  com.summer.icore.service.UserRoleService;
import  com.summer.icore.dao.UserRoleMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> findUserRoleByMap() {
        Map<String,Object> filters = new HashMap<>();
        return userRoleMapper.selectByMap(filters);
    }
}
