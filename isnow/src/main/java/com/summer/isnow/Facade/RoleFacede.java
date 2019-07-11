package com.summer.isnow.Facade;

import com.summer.icore.model.UserRole;
import com.summer.icore.service.UserRoleService;
import com.summer.isnow.dto.UserRoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/10 17:09
 */
@Component
public class RoleFacede {

    @Autowired
    private UserRoleService userRoleService;

    public List<UserRoleView> listRole(){
          List<UserRole> userRoleList = userRoleService.findUserRoleByMap();
          List<UserRoleView> userRoleViews = new ArrayList<>();
          for(UserRole userRole : userRoleList){
              UserRoleView userRoleView = new UserRoleView();
              userRoleView.setName(userRole.getName());
              userRoleView.setCategory(userRole.getCategory());
              userRoleView.setDescription(userRole.getDescription());
              userRoleView.setSort(userRole.getSort());
              userRoleViews.add(userRoleView);
          }
          return userRoleViews;
    }
}
