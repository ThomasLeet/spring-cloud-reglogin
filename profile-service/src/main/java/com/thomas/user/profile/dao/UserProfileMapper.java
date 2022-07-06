package com.thomas.user.profile.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thomas.user.profile.model.UserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProfileMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UserProfile record);

    int insertSelective(UserProfile record);

    int insertOrUpdate(UserProfile record);

    UserProfile selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserProfile record);

    int updateByPrimaryKey(UserProfile record);

    int updateStatusByUids(@Param("uids") List<Long> uids, @Param("deleted")Boolean deleted, @Param("updateTime")Long updateTime);

    Long selectUidByNickname(String nickname);
}