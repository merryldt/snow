package com.summer.isnow.api;

import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icommon.utils.StringUtil;
import com.summer.icommon.utils.WeiXinUtil;
import com.summer.icore.model.WxUser;
import com.summer.isnow.Facade.WxUserFacade;
import com.summer.isnow.dto.WxUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liudongting
 * @date 2019/8/2 14:54
 */
@RestController
@RequestMapping(value = "api/wxUser")
public class WxUserApi {

    @Autowired
    private WxUserFacade wxUserFacade;

    //登录
    @RequestMapping(value = "/wxLogin",method = RequestMethod.POST)
    public ResponseModel wxLogin(@RequestBody WxUserView wxUserView){
        if(! StringUtil.isNull(wxUserView.getCode())){
            return new ResponseModel(-2,"参数为空");
        }
        ResponseModel responseModel = wxUserFacade.wxLogin(wxUserView.getCode());
        return responseModel;
    }
}
