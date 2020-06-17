package com.item_backend.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.item_backend.config.JwtConfig;
import com.item_backend.mapper.UserTypeMapper;
import com.item_backend.model.entity.UserType;
import com.item_backend.service.UserTypeService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeMapper userTypeMapper;

    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("myCacheManager")
    CacheManager cacheManager;

    @Override
    public Boolean addUserType( UserType userType) {
            if (StringUtils.isEmpty(userType.getU_type_name())){
                return false;
            }
        System.out.println(userType);
          Integer change=  userTypeMapper.addUserType(userType);
          if (change>0){
              cacheManager.getCache("userType").clear();
              return true;
          }

        return false;
    }

    @Override
    public Boolean deleteUserType( Integer u_type) {
        if (u_type==null||u_type==1||u_type==6){
            return false;
        }
        Integer change=  userTypeMapper.deleteUserTypeByUType(u_type);
        if (change>0) {
            cacheManager.getCache("userType").clear();
            return true;
        }


        return false;
    }

    @Override
    @Cacheable(cacheNames = "userType",key = "#u_id")
    public List<UserType> getAllUserType(Integer u_id) {
        //只有校级管理员和超级管理员有这个权限。
        if (u_id<=0){
            return null;
        }

          return  userTypeMapper.getAllUserType( u_id);
    }

    @Override
    public Boolean updateUserType(UserType userType) {
        if (userType.getU_type() == null || userType.getU_type()==1){
            return  false;
        }
        Integer changerow =  userTypeMapper.updateUserType(userType);
        if (changerow > 0){
            cacheManager.getCache("userType").clear();
            return true;
        }
        return false;
    }

}
