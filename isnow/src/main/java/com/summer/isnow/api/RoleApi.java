package com.summer.isnow.api;

import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;
import com.summer.icore.model.UserRole;
import com.summer.isnow.Facade.RoleFacede;
import com.summer.isnow.dto.UserRoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/10 17:07
 */
@RequestMapping("sys/role")
@RestController
public class RoleApi extends  BaseApi {

    @Autowired
    private RoleFacede roleFacede;

    @RequestMapping(value = "/read",method = RequestMethod.GET)
    public ResponseModel read(){
        List<UserRoleView> userRoleViewList = roleFacede.listRole();
        return new ResponseModel(ResponseCode.SUCCESS,userRoleViewList);
    }
}
