package com.summer.icore.service;
import com.baomidou.mybatisplus.service.IService;
import com.summer.icore.model.WxUser;
public interface WxUserService extends IService<WxUser> {
    WxUser getWxUserByOpenId(String openId);
    WxUser saveWxUser(WxUser wxUser);
    WxUser updateWxUser(WxUser wxUser);
}
