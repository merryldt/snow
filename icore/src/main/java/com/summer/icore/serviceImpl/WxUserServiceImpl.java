package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.icommon.utils.StringUtil;
import com.summer.icore.dao.UserMapper;
import com.summer.icore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.summer.icore.model.WxUser;
import  com.summer.icore.service.WxUserService;
import  com.summer.icore.dao.WxUserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper,WxUser> implements WxUserService{

    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public WxUser getWxUserByOpenId(String openId) {
        return wxUserMapper.getWxUserByOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxUser saveWxUser(WxUser wxUser) {
       Integer result = wxUserMapper.insert(wxUser);
       if(result==0){
           return null;
       }
        return wxUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxUser updateWxUser(WxUser wxUser) {
        Integer result =0;
        result = wxUserMapper.updateById(wxUser);
        if(StringUtil.isNull(wxUser.getUserId())){
            User user = userMapper.selectById(wxUser.getUserId());
            if(null != user){
                user.setLoginTime(new Date());
                result = userMapper.updateById(user);
            }
        }
        if(result==0){
            return null;
        }
        return wxUser;
    }
}
