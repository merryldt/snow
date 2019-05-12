package com.summer.icore.service;

import com.baomidou.mybatisplus.service.IService;
import com.summer.icore.model.User;

import java.util.List;

public interface UserService extends IService<User> {
    public String generateJwtToken(String username);
    public User getJwtTokenInfo(String username);
    public void deleteLoginInfo(String username);
    public User getUserInfo(String userName);
    public List<String> getUserRoles(Long userId);

}
