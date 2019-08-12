package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.icommon.exception.GeneralException;
import com.summer.icommon.utils.JwtUtils;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icore.dao.UserAdminRoleRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserAdmin;
import  com.summer.icore.service.UserAdminService;
import  com.summer.icore.dao.UserAdminMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper,UserAdmin> implements UserAdminService{

    @Autowired
    private  UserAdminMapper userAdminMapper;
    @Autowired
    private UserAdminRoleRelationMapper userAdminRoleRelationMapper;

    @Override
    public String generateJwtToken(String username)  {
        UserAdmin user = userAdminMapper.getUserAdminInfo(username);
        /**
         * @todo 将salt保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
         */
        System.out.println("username : salt " +username +"---"+ user.getSalt() );
        //生成jwt token，设置过期时间为1小时
        return JwtUtils.sign(username, user.getSalt(), 3600L);
    }

    @Override
    public UserAdmin getJwtTokenInfo(String username) {
       return getUserAdminInfo(username);
    }

    @Override
    public void deleteLoginInfo(String username) {

    }

    @Override
    public UserAdmin getUserAdminInfo(String userName) {
        UserAdmin user = userAdminMapper.getUserAdminInfo(userName);
        return user;
    }

    @Override
    public List<String> getUserAdminRoles(Integer userId) {
        List<String> roles = new ArrayList<>();
        String stringList = userAdminRoleRelationMapper.getUserAdminRoles(userId);
        String [] aa = stringList.split(",");
        for(String s : aa){
            roles.add(s);
        }
        return roles;
    }

    @Override
    public UserAdmin getUserAdminByName(String userName) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("username",userName);
        List<UserAdmin> userAdminList = userAdminMapper.selectByMap(filters);
        if(null != userAdminList && userAdminList.size()>0){
            return userAdminList.get(0);
        }
        return null;
    }
}
