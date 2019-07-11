package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserAdminRoleRelation;
import  com.summer.icore.service.UserAdminRoleRelationService;
import  com.summer.icore.dao.UserAdminRoleRelationMapper;
@Service
public class UserAdminRoleRelationServiceImpl extends ServiceImpl<UserAdminRoleRelationMapper,UserAdminRoleRelation> implements UserAdminRoleRelationService{
}
