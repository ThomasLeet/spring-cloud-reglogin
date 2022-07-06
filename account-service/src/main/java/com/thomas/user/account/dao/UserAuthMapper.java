package com.thomas.user.account.dao;

import com.thomas.user.account.model.UserAuth;
import org.apache.ibatis.annotations.Param;

public interface UserAuthMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserAuth record);

    int updateByPrimaryKey(UserAuth record);

    UserAuth selectByEmail(@Param("email")String email);

    UserAuth selectByPhone(@Param("countryCode")String cCode,@Param("phone")String phone);

    Long selectUidByEmail(@Param("email")String email);

    Long selectUidByPhone(@Param("countryCode")String cCode,@Param("phone")String phone);
}