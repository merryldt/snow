package com.summer.icore.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.summer.icore.model.WxUser;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper extends BaseMapper<WxUser> {
    WxUser getWxUserByOpenId(@Param("openId") String openId);
}
