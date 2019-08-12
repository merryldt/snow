package com.summer.icore.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.icommon.utils.JwtUtils;
import com.summer.icore.dao.UserMapper;
import com.summer.icore.model.User;
import com.summer.icore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息接口
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


	@Autowired
	private UserMapper usermapper;
   
    /**
     * 保存user登录信息，返回token
     * @param userDto
     */
    @Override
    public String generateJwtToken(String username) {
		User user = usermapper.getUserInfo(username);
    	/**
    	 * @todo 将salt保存到数据库或者缓存中
    	 * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
    	 */   	
    	return JwtUtils.sign(username, user.getSalt(), 3600L); //生成jwt token，设置过期时间为1小时
    }
    
    /**
     * 获取上次token生成时的salt值和登录用户信息
     * @param username
     * @return
     */
	@Override
    public User getJwtTokenInfo(String username) {
    	/**
		 *
		 * pp
    	 * @todo 从数据库或者缓存中取出jwt token生成时用的salt
    	 * salt = redisTemplate.opsForValue().get("token:"+username);
    	 */   	
    	User user = getUserInfo(username);
    	return user;
    }

    /**
     * 清除token信息
     * @param userName 登录用户名
     */
	@Override
    public void deleteLoginInfo(String userName) {
    	/**
    	 * @todo 删除数据库或者缓存中保存的salt
    	 * redisTemplate.delete("token:"+username);
    	 */
    	
    }
    
    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     * @param userName
     * @return
     */
	@Override
    public User getUserInfo(String userName) {
    	User user = usermapper.getUserInfo(userName);
    	return user;
    }

    /**
     * 获取用户角色列表，强烈建议从缓存中获取
     * @param userId
     * @return
     */
	@Override
    public List<String> getUserRoles(Long userId){
    	return Arrays.asList("admin");
    }

	@Override
	public User getUserByName(String userName) {
		Map<String,Object> filters = new HashMap<>();
		filters.put("user_name",userName);
		return usermapper.selectByMap(filters).get(0);
	}
}
