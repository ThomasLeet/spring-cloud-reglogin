package com.thomas.user.profile.client;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.profile.dto.ProfileVO;
import com.thomas.user.profile.utils.ProfileVoHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(
        headers = {UserHeaders.AGENT_TYPE + "=${user.header.agent_type}", UserHeaders.USER_TOKEN + "=${user.header.token}"})
public class ProfileDegradeClient implements ProfileClient {

    @Override
    public BaseResponse<ProfileVO> getInternalUid(Long uid) {
        return getDefaultResponse(uid);
    }

    @Override
    public BaseResponse<ProfileVO> create(ProfileVO profile) {
        return getDefaultResponse(profile.getUid());
    }

    @Override
    public BaseResponse<ProfileVO> update(ProfileVO profile) {
        return getDefaultResponse(profile.getUid());
    }

    @Override
    public BaseResponse<BooleanView> delete(Long uid) {
        return getDefaultBooleanResponse();
    }

    @Override
    public BaseResponse<BooleanView> batchDelete(List<Long> uids) {
        return getDefaultBooleanResponse();
    }

    public BaseResponse<ProfileVO> getDefaultResponse(Long uid) {
        if (uid == null || uid <= 0) {
            return null;
        }
        BaseResponse<ProfileVO> response = new BaseResponse<>();
        response.setCode(ResultCode.SERVICE_DEGRADE.getCode());
        response.setType(ResultCode.SERVICE_DEGRADE.getType());
        response.setMessage(ResultCode.SERVICE_DEGRADE.getMsg());
        response.setData(getDefaultVo(uid));
        return response;
    }

    public BaseResponse<BooleanView> getDefaultBooleanResponse() {
        BaseResponse<BooleanView> response = new BaseResponse<>();
        response.setCode(ResultCode.SERVICE_DEGRADE.getCode());
        response.setType(ResultCode.SERVICE_DEGRADE.getType());
        response.setMessage(ResultCode.SERVICE_DEGRADE.getMsg());
        response.setData(new BooleanView(false));
        return response;
    }

    public ProfileVO getDefaultVo(Long uid) {
        ProfileVO profileVO = new ProfileVO();
        profileVO.setUid(uid);
        ProfileVoHelper.setDefaultIcon(profileVO);
        ProfileVoHelper.setDefaultNickname(profileVO);
        return profileVO;
    }
}
