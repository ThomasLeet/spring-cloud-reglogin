package com.thomas.user.admin.dao;

import com.thomas.user.admin.model.UserAdmin;

public interface UserAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAdmin record);

    int insertSelective(UserAdmin record);

    UserAdmin selectByPrimaryKey(Integer id);

    UserAdmin selectByName(String name);

    int updateByPrimaryKeySelective(UserAdmin record);

    int updateByPrimaryKey(UserAdmin record);
}