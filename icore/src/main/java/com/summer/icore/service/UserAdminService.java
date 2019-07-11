package com.summer.icore.service;
import com.baomidou.mybatisplus.service.IService;
import com.summer.icore.model.UserAdmin;

import java.util.List;

public interface UserAdminService extends IService<UserAdmin> {
    String generateJwtToken(String username);
    UserAdmin getJwtTokenInfo(String username);
    void deleteLoginInfo(String username);
    UserAdmin getUserAdminInfo(String userName);
    List<String> getUserAdminRoles(Integer userId);
    UserAdmin getUserAdminByName(String userName);
}
