package com.item_backend.mapper;

import com.item_backend.model.entity.FacultyAndUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacultyAndUserMapper {
   List<FacultyAndUser> findAllFacultyInfo();
}
