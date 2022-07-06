package com.thomas.user.admin.service;

import com.thomas.common.api.ResultCode;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.common.secure.AdminSecureAuthCookie;
import com.thomas.common.utils.IPHelper;
import com.thomas.common.utils.PwdHelper;
import com.thomas.common.utils.ResponseHelper;
import com.thomas.common.utils.TimeHelper;
import com.thomas.user.account.client.AccountClient;
import com.thomas.user.admin.dao.UserAdminMapper;
import com.thomas.user.admin.dto.AdminLoginVO;
import com.thomas.user.admin.model.UserAdmin;
import com.thomas.user.profile.client.ProfileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AdminService {
    @Autowired
    UserAdminMapper userAdminMapper;
    @Autowired
    AccountClient accountClient;
    @Autowired
    ProfileClient profileClient;

    public UserAdmin login(AdminLoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        UserAdmin userAdmin = userAdminMapper.selectByName(loginVO.getName());
        if (userAdmin == null) {
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        if (loginVO.getPwd() == null || !PwdHelper.checkPasswd(loginVO.getPwd(), userAdmin.getPassword())) {

            throw ExceptionHelper.createUserBizException(ResultCode.PASSWORD_INCORRECT);
        }
        //set auth cookie
        afterRegLogin(userAdmin.getId(), userAdmin.getPassword(), request, response);
        return userAdmin;
    }

    public void afterRegLogin(long uid, String password, HttpServletRequest request, HttpServletResponse response) {
        //set auth cookie
        String authcookie = AdminSecureAuthCookie.genSAuthcookie(uid, TimeHelper.currentTimeSeconds(), password, IPHelper.getRemoteAddr(request), null);
        ResponseHelper.setAdminLoginCookieHttpOnly(request, response, authcookie, null);
    }

    public boolean batchDelete(List<Long> uids) {
        accountClient.batchDelete(uids);
        profileClient.batchDelete(uids);
        return true;
    }
}
