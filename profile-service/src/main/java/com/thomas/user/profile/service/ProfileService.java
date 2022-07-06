package com.thomas.user.profile.service;

import com.thomas.common.api.ResultCode;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.common.exception.UserBizException;
import com.thomas.common.utils.XssUtils;
import com.thomas.user.profile.dao.UserProfileMapper;
import com.thomas.user.profile.model.UserProfile;
import com.thomas.user.profile.utils.UserProfileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileService {
    @Autowired
    UserProfileMapper  userProfileMapper;

    public UserProfile getByUid(Long uid,boolean filter){
        UserProfile profile = userProfileMapper.selectByPrimaryKey(uid);
        if(profile == null){
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        if(filter && profile.getDeleted() || !profile.getActivated()){
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        return profile;
    }

    public int insert(UserProfile profile){
        if(profile == null){
            return 0;
        }
        UserProfileHelper.setDefaultIcon(profile);
        UserProfileHelper.setDefaultNickname(profile);
        profile.setCreateTime(System.currentTimeMillis());
        profile.setUpdateTime(System.currentTimeMillis());
        try {
            return userProfileMapper.insertOrUpdate(profile);
        } catch (DuplicateKeyException e) {
            throw ExceptionHelper.createUserBizException(ResultCode.USER_EXIST);
        }
    }

    public int update(UserProfile profile){
        if(profile == null){
            return 0;
        }
        try {
            //被删除用户的昵称也不应被占用
            UserProfileHelper.setXssSafePerperties(profile);
            profile.setUpdateTime(System.currentTimeMillis());
            return userProfileMapper.updateByPrimaryKeySelective(profile);
        }  catch (DuplicateKeyException e) {
            throw ExceptionHelper.createUserBizException(ResultCode.NICKNAME_CONFLICT);
        }
    }

    public boolean delete(Long uid){
        UserProfile userProfile = new UserProfile();
        userProfile.setUid(uid);
        userProfile.setDeleted(true);
        userProfile.setUpdateTime(System.currentTimeMillis());
        userProfileMapper.updateByPrimaryKeySelective(userProfile);
        return true;
    }

    public boolean deleteByUids(List<Long> uids){
        int rows = userProfileMapper.updateStatusByUids(uids, true, System.currentTimeMillis());
        return true;
    }

}
