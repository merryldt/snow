package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserRolePermissionRelation;
import  com.summer.icore.service.UserRolePermissionRelationService;
import  com.summer.icore.dao.UserRolePermissionRelationMapper;
@Service
public class UserRolePermissionRelationServiceImpl extends ServiceImpl<UserRolePermissionRelationMapper,UserRolePermissionRelation> implements UserRolePermissionRelationService{
}
