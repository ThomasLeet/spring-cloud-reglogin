package com.thomas.user.account.service;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.ErrorType;
import com.thomas.common.api.ResultCode;
import com.thomas.common.dto.BooleanView;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.user.profile.client.ProfileClient;
import com.thomas.user.profile.dto.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ProfileService {

    static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    ProfileClient profileClient;

    public ProfileVO getUid(Long uid) {
        BaseResponse<ProfileVO> response = profileClient.getInternalUid(uid);
        if (response != null) {
            if (response.isSuccess()) {
                return response.getData();
            }
        }
        return null;
    }

    public ProfileVO getInternalUid(Long uid,boolean getDegrade) {
        BaseResponse<ProfileVO> response = profileClient.getInternalUid(uid);
        if (response != null) {
            if (response.isSuccess()) {
                return response.getData();
            }else if(response.getCode() ==ResultCode.SERVICE_DEGRADE.getCode()
                        && getDegrade){
                return response.getData();
            }
        }
        return null;
    }

    public ProfileVO create(ProfileVO profile) {
        BaseResponse<ProfileVO> response = profileClient.create(profile);
        if (response != null) {
            if (response.isSuccess()) {
                return response.getData();
            } else if (response.getCode() == HttpServletResponse.SC_BAD_REQUEST) {
                if (ErrorType.NICKNAME_CONFLICT.equals(response.getType())) {
                    throw ExceptionHelper.createUserBizException(ResultCode.NICKNAME_CONFLICT);
                } else if (ErrorType.USER_EXIST.equals(response.getType())) {
                    throw ExceptionHelper.createUserBizException(ResultCode.USER_EXIST);
                }
            }
        }
        return null;
    }

    public ProfileVO update(ProfileVO profile) {
        BaseResponse<ProfileVO> response = profileClient.update(profile);
        if (response != null) {
            if (response.isSuccess()) {
                return response.getData();
            } else if (response.getCode() == HttpServletResponse.SC_BAD_REQUEST) {
                if (ErrorType.NICKNAME_CONFLICT.equals(response.getType())) {
                    throw ExceptionHelper.createUserBizException(ResultCode.NICKNAME_CONFLICT);
                }
            }
        }
        return null;
    }

    public boolean delete(Long uid) {
        BaseResponse<BooleanView> response = profileClient.delete(uid);
        if (response == null) {
            return false;
        }
        return response.isSuccess();
    }

    public boolean batchDelete(List<Long> uids) {
        BaseResponse<BooleanView> response = profileClient.batchDelete(uids);
        if (response == null) {
            return false;
        }
        return response.isSuccess();
    }
}
