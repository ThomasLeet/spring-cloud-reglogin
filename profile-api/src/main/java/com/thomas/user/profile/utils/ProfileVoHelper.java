package com.thomas.user.profile.utils;

import com.thomas.common.constants.UserConstants;
import com.thomas.user.profile.dto.ProfileVO;
import org.apache.commons.lang3.StringUtils;

public class ProfileVoHelper {
    public static void setDefaultIcon(ProfileVO profile){
        if(profile == null){
            return;
        }
        if(StringUtils.isBlank(profile.getIcon())){
            profile.setIcon(UserConstants.DEFALUT_ICON);
        }
        return;
    }

    public static void setDefaultNickname(ProfileVO profile){
        if(profile == null){
            return;
        }
        if(StringUtils.isBlank(profile.getNickname())){
            profile.setNickname(UserConstants.DEFALUT_NICKNAME_PREFIX+(profile.getUid().longValue() % 1000000));
        }
        return;
    }
}
