package com.summer.isnow.Facade;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icommon.utils.StringUtil;
import com.summer.icommon.utils.WeiXinUtil;
import com.summer.icore.model.WxUser;
import com.summer.icore.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/8/2 14:08
 */
@Component
public class WxUserFacade extends comFacade {

    @Autowired
    private WxUserService wxUserService;
    /**
     * 登录
     */
    public ResponseModel wxLogin(String code){
        Map<String,Object> map =new HashMap<>();
        String openId = WeiXinUtil.getMiniLogin(code);
        WxUser wxUser = null;
        if(StringUtil.isNull(openId)){
            wxUser = wxUserService.getWxUserByOpenId(openId);
            if(null == wxUser){
                 wxUser = new WxUser();
                 wxUser.setOpenId(openId);
                 wxUser.setCreateTime(new Date());
                 wxUser.setUpdateTime(new Date());
                 wxUser.setStatus(0);
                wxUser = wxUserService.saveWxUser(wxUser);
            }else {
                wxUser.setUpdateTime(new Date());
                wxUser = wxUserService.updateWxUser(wxUser);
            }
        }
       if(null != wxUser){
           map.put("wxUser",wxUser);
            return new ResponseModel(ResponseCode.OK,wxUser);
       }
       return new ResponseModel(-1,"用户认证失败");
    }
}
