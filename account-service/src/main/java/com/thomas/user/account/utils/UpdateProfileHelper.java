package com.thomas.user.account.utils;

import com.thomas.user.account.dto.ProfileView;
import com.thomas.user.account.dto.UpdateProfileVO;
import com.thomas.user.profile.dto.ProfileVO;

public class UpdateProfileHelper {
    public static ProfileVO convertToProfileVO(UpdateProfileVO updateProfileVO) {
        if (updateProfileVO == null) {
            return null;
        }
        ProfileVO vo = new ProfileVO();
        if (updateProfileVO.getUid() != null) {
            vo.setUid(updateProfileVO.getUid());
        }
        if (updateProfileVO.getIcon() != null) {
            vo.setIcon(updateProfileVO.getIcon());
        }
        if (updateProfileVO.getNickname() != null) {
            vo.setNickname(updateProfileVO.getNickname());
        }
        return vo;
    }

    public static ProfileView convertToView(ProfileVO vo) {
        ProfileView view = new ProfileView();
        if (vo == null) {
            return null;
        }
        if (vo.getUid() != null) {
            view.setUid(vo.getUid());
        }
        if (vo.getIcon() != null) {
            view.setIcon(vo.getIcon());
        }
        if (vo.getNickname() != null) {
            view.setNickname(vo.getNickname());
        }
        return view;
    }
}
