package com.item_backend.service;


import com.item_backend.model.entity.UserType;

import java.util.List;

public interface UserTypeService {
   Boolean addUserType(UserType userType);
  Boolean deleteUserType ( Integer u_type);
  List<UserType> getAllUserType(Integer u_id);
  Boolean updateUserType(UserType userType);
}
