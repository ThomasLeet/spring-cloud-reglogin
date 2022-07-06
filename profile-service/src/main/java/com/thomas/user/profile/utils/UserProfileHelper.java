package com.thomas.user.profile.utils;

import com.thomas.common.constants.UserConstants;
import com.thomas.common.utils.XssUtils;
import com.thomas.user.profile.dto.ProfileVO;
import com.thomas.user.profile.model.UserProfile;
import org.apache.commons.lang3.StringUtils;

public class UserProfileHelper {

    public static UserProfile convertToUserProfile(ProfileVO profileVO){
        if(profileVO == null){
            return null;
        }
        UserProfile userProfile = new UserProfile();
        if(profileVO.getIcon() != null){
            userProfile.setIcon(profileVO.getIcon());
        }
        if(profileVO.getNickname() != null){
            userProfile.setNickname(profileVO.getNickname());
        }
        if(profileVO.getUid() != null){
            userProfile.setUid(profileVO.getUid());
        }
        if(profileVO.getDeleted() != null){
            userProfile.setDeleted(profileVO.getDeleted());
        }
        if(profileVO.getActivated() != null){
            userProfile.setActivated(profileVO.getActivated());
        }
        return userProfile;
    }

    public static ProfileVO convertToVO(UserProfile userProfile){
        if(userProfile == null){
            return null;
        }
        ProfileVO vo = new ProfileVO();
        if(userProfile.getUid() != null){
            vo.setUid(userProfile.getUid());
        }
        if(userProfile.getIcon() != null){
            vo.setIcon(userProfile.getIcon());
        }
        if(userProfile.getNickname() != null){
            vo.setNickname(userProfile.getNickname());
        }
        if(userProfile.getDeleted() != null){
            vo.setDeleted(userProfile.getDeleted());
        }
        if(userProfile.getActivated() != null){
            vo.setActivated(userProfile.getActivated());
        }
        if(userProfile.getCreateTime() != null){
            vo.setCreateTime(userProfile.getCreateTime());
        }
        return vo;
    }

    public static void setDefaultIcon(UserProfile profile){
        if(profile == null){
            return;
        }
        if(StringUtils.isBlank(profile.getIcon())){
            profile.setIcon(UserConstants.DEFALUT_ICON);
        }else{
            profile.setIcon(XssUtils.getSafeStringXSS(profile.getIcon()));
        }
        return;
    }

    public static void setDefaultNickname(UserProfile profile){
        if(profile == null){
            return;
        }
        if(StringUtils.isBlank(profile.getNickname())){
            profile.setNickname(UserConstants.DEFALUT_NICKNAME_PREFIX+(profile.getUid().longValue() % 1000000));
        }else{
            profile.setNickname(XssUtils.getSafeStringXSS(profile.getNickname()));
        }
        return;
    }

    public static void setXssSafePerperties(UserProfile profile){
        if(profile == null){
            return;
        }
        profile.setIcon(XssUtils.getSafeStringXSS(profile.getIcon()));
        profile.setNickname(XssUtils.getSafeStringXSS(profile.getNickname()));
    }
}
