package com.summer.icore.serviceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.summer.icore.model.UserMember;
import  com.summer.icore.service.UserMemberService;
import  com.summer.icore.dao.UserMemberMapper;
@Service
public class UserMemberServiceImpl extends ServiceImpl<UserMemberMapper,UserMember> implements UserMemberService{
}
