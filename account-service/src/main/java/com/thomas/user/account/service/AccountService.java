package com.thomas.user.account.service;

import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.ServiceConstants;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.common.secure.SecureAuthCookie;
import com.thomas.common.secure.SensitiveCipher;
import com.thomas.common.utils.IPHelper;
import com.thomas.common.utils.IdHelper;
import com.thomas.common.utils.PwdHelper;
import com.thomas.common.utils.TimeHelper;
import com.thomas.user.account.utils.ControllerHelper;
import com.thomas.user.account.dao.UserAuthMapper;
import com.thomas.user.account.dto.LoginVO;
import com.thomas.user.account.dto.RegVO;
import com.thomas.user.account.dto.UpdateProfileVO;
import com.thomas.user.account.model.UserAuth;
import com.thomas.user.account.utils.DeleteStatus;
import com.thomas.user.account.utils.IdGenerator;
import com.thomas.user.account.utils.UpdateProfileHelper;
import com.thomas.user.account.utils.UserAuthHelper;
import com.thomas.user.profile.dto.ProfileVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AccountService {

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    ProfileService profileService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordService passwordService;

    public ProfileVO login(LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        UserAuth userAuth = null;

        if (StringUtils.isNotEmpty(loginVO.getEmail())) {
            //email
            if (loginVO.getEmail() != null) {
                userAuth = userAuthMapper.selectByEmail(loginVO.getEmail());
                if (userAuth == null || UserAuthHelper.isDeleted(userAuth)) {
                    throw ExceptionHelper.createUserBizException(ResultCode.EMAIL_NOT_EXIST);
                }
            }
        } else {
            //phone
            userAuth = userAuthMapper.selectByPhone(loginVO.getCountryCode(), loginVO.getPhone());
            if (userAuth == null || UserAuthHelper.isDeleted(userAuth)) {
                throw ExceptionHelper.createUserBizException(ResultCode.PHONE_NOT_EXIST);
            }
        }
        if (!UserAuthHelper.isActivated(userAuth)) {
            throw ExceptionHelper.createUserBizException(ResultCode.EMAIL_NOT_ACTIVATED);
        }
        //check password
        if (StringUtils.isEmpty(userAuth.getPassword()) || StringUtils.isEmpty(loginVO.getPwd())
                || !passwordService.checkPasswd(loginVO.getPwd(), userAuth.getPassword())) {
            throw ExceptionHelper.createUserBizException(ResultCode.PASSWORD_INCORRECT);
        }
        ProfileVO propfileVO = profileService.getInternalUid(userAuth.getUid(), false);
        //修复注册时插入用户信息失败，注意区分网络或Profile服务问题导致的失败
        if (propfileVO == null) {
            ProfileVO userProfile = new ProfileVO();
            userProfile.setActivated(userAuth.getActivated());
            userProfile.setDeleted(UserAuthHelper.isDeleted(userAuth));
            userProfile.setUid(userAuth.getUid());
            propfileVO = profileService.create(userProfile);
        }
        //set auth cookie
        afterRegLogin(userAuth.getUid(), userAuth.getPassword(), request, response);
        return propfileVO;
    }

    /**
     * 未激活、被管理员删除的用户不支持再注册
     *
     * @param regVo
     * @param request
     * @param response
     * @return
     */
    public ProfileVO register(RegVO regVo, HttpServletRequest request, HttpServletResponse response) {
        UserAuth userAuth = null;
        UserAuth newUserAuth = new UserAuth();
        if (ServiceConstants.ACCOUNT_EMAIL_REGISTER.equals(regVo.getRegType())) {
            userAuth = userAuthMapper.selectByEmail(regVo.getEmail());
            if (userAuth != null) {
                if (!UserAuthHelper.isDeleted(userAuth)) {
                    throw ExceptionHelper.createUserBizException(ResultCode.EMAIL_CONFLICT);
                }
            } else {
                newUserAuth.setEmail(regVo.getEmail());
            }
            newUserAuth.setActivated(false);
        } else if (ServiceConstants.ACCOUNT_PHONE_REGISTER.equals(regVo.getRegType())) {
            userAuth = userAuthMapper.selectByPhone(regVo.getCountryCode(), SensitiveCipher.encrypt(regVo.getPhone()));
            if (userAuth != null) {
                if (!UserAuthHelper.isDeleted(userAuth)) {
                    throw ExceptionHelper.createUserBizException(ResultCode.PHONE_CONFLICT);
                }
            } else {
                newUserAuth.setCountryCode(regVo.getCountryCode());
                newUserAuth.setPhone(SensitiveCipher.encrypt(regVo.getPhone()));
            }
            newUserAuth.setActivated(true);
        } else {
            throw ExceptionHelper.createUserBizException(ResultCode.PARAM_TYPE_ERROR);
        }
        //注册
        Long uid = userAuth != null ? userAuth.getUid() : IdGenerator.getUid();
        newUserAuth.setUid(uid);
        newUserAuth.setPassword(passwordService.encryptPasswd(regVo.getPwd()));
        newUserAuth.setDeleted(DeleteStatus.DEFAULT_STATUS);
        if (userAuth == null) {
            newUserAuth.setCreateTime(System.currentTimeMillis());
        }
        newUserAuth.setUpdateTime(System.currentTimeMillis());
        int rows = 0;
        if (userAuth != null) {
            //管理员删除用户无法再注册。用户自己删除后重新激活的，邮箱还需要再确认，密码也重置。
            if (UserAuthHelper.isAdminDeleted(userAuth.getDeleted())) {
                throw ExceptionHelper.createUserBizException(ResultCode.USER_BE_FROZEN);
            }
            rows = userAuthMapper.updateByPrimaryKeySelective(newUserAuth);
        }
        rows = userAuthMapper.insert(newUserAuth);
        //用户信息
        if (rows > 0) {
            ProfileVO userProfile = new ProfileVO();
            userProfile.setActivated(newUserAuth.getActivated());
            userProfile.setDeleted(UserAuthHelper.isDeleted(newUserAuth));
            userProfile.setUid(uid);
            ProfileVO propfileVO = profileService.create(userProfile);
            if (ServiceConstants.ACCOUNT_EMAIL_REGISTER.equals(regVo.getRegType())) {
                //email
                emailService.sendHtmlMail(regVo.getEmail(), uid);
                return propfileVO;
            } else {
                //手机注册直接登录态
                afterRegLogin(userAuth.getUid(), passwordService.encryptPasswd(userAuth.getPassword()), request, response);
            }
            return propfileVO;
        }
        return null;
    }

    public boolean activate(String code) {
        long uid = IdHelper.decodeEmailId(code);
        if (uid <= 0L) {
            throw ExceptionHelper.createUserBizException(ResultCode.PARAM_VALID_ERROR);
        }
        UserAuth userAuth = userAuthMapper.selectByPrimaryKey(uid);
        if (userAuth != null) {
            if (UserAuthHelper.isDeleted(userAuth)) {
                throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
            }
            userAuth = new UserAuth();
            userAuth.setUid(uid);
            userAuth.setActivated(true);
            userAuth.setUpdateTime(System.currentTimeMillis());
            userAuthMapper.updateByPrimaryKeySelective(userAuth);
            ProfileVO profileVO = new ProfileVO();
            profileVO.setActivated(true);
            profileVO.setUid(uid);
            profileService.update(profileVO);
            return true;
        }
        return false;
    }

    public boolean sendEmail(String email) {
        UserAuth userAuth = userAuthMapper.selectByEmail(email);
        if (userAuth == null || UserAuthHelper.isDeleted(userAuth)) {
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        if (UserAuthHelper.isActivated(userAuth)) {
            return true;
        }
        return emailService.sendHtmlMail(email, userAuth.getUid());
    }

    public ProfileVO update(UpdateProfileVO updateProfileVO) {
        if (updateProfileVO == null) {
            throw ExceptionHelper.createUserBizException(ResultCode.PARAM_VALID_ERROR);
        }
        ProfileVO profileVO = UpdateProfileHelper.convertToProfileVO(updateProfileVO);
        checkProperties(updateProfileVO);
        UserAuth userAuth = userAuthMapper.selectByPrimaryKey(updateProfileVO.getUid());
        if (userAuth == null || UserAuthHelper.isDeleted(userAuth) || !UserAuthHelper.isActivated(userAuth)) {
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        //pwd
        if (updateProfileVO.getOldPwd() != null) {
            if (!passwordService.checkPasswd(updateProfileVO.getOldPwd(), userAuth.getPassword())) {
                throw ExceptionHelper.createUserBizException(ResultCode.PASSWORD_INCORRECT);
            } else if (StringUtils.isEmpty(updateProfileVO.getNewPwd()) || StringUtils.isEmpty(updateProfileVO.getConfirmPwd())
                    || !updateProfileVO.getNewPwd().equals(updateProfileVO.getConfirmPwd())) {
                throw ExceptionHelper.createUserBizException(ResultCode.PASSWORD_INCONSISTENT);
            } else if (!PwdHelper.validatePassword(updateProfileVO.getNewPwd())) {
                throw ExceptionHelper.createUserBizException(ResultCode.PASSWORD_INCORRECT_FORMAT);
            }
            userAuth = new UserAuth();
            userAuth.setUid(updateProfileVO.getUid());
            userAuth.setPassword(passwordService.encryptPasswd(updateProfileVO.getNewPwd()));
            userAuthMapper.updateByPrimaryKeySelective(userAuth);
        }
        //icon or nickname
        ProfileVO oldProfileVO = profileService.getInternalUid(updateProfileVO.getUid(), false);
        if (oldProfileVO != null) {
            if (profileVO.getIcon() != null || profileVO.getNickname() != null) {
                return profileService.update(profileVO);
            } else {
                return profileService.getInternalUid(profileVO.getUid(), true);
            }
        }
        return profileService.create(profileVO);
    }

    private void checkProperties(UpdateProfileVO updateProfileVO) {
        if (updateProfileVO.getIcon() != null && updateProfileVO.getIcon().length() > 200) {
            throw ExceptionHelper.createAnnotationException(ResultCode.PARAM_VALID_ERROR, "Icon url is too long");
        }
        if (updateProfileVO.getNickname() != null && updateProfileVO.getNickname().length() > 50) {
            throw ExceptionHelper.createAnnotationException(ResultCode.PARAM_VALID_ERROR, "nickname is too long");
        }
        if (updateProfileVO.getNewPwd() != null && updateProfileVO.getNewPwd().length() > 30) {
            throw ExceptionHelper.createAnnotationException(ResultCode.PARAM_VALID_ERROR, "The password is too long");
        }
    }

    public void delete(Long uid) {
        UserAuth userAuth = userAuthMapper.selectByPrimaryKey(uid);
        if (userAuth == null) {
            throw ExceptionHelper.createUserBizException(ResultCode.USER_NOT_EXIST);
        }
        UserAuth auth = new UserAuth();
        UserAuthHelper.userDeletedStatus(userAuth);
        auth.setUid(uid);
        auth.setDeleted(userAuth.getDeleted());
        auth.setUpdateTime(System.currentTimeMillis());
        userAuthMapper.updateByPrimaryKeySelective(auth);
        profileService.delete(uid);
    }

    /**
     * 循环删除为了保留用户删除态
     *
     * @param uids
     * @return
     */
    public boolean adminDeleteUids(List<Long> uids) {
        if (CollectionUtils.isEmpty(uids)) {
            return true;
        }
        for (Long uid : uids) {
            if (uid == null || uid <= 0) {
                continue;
            }
            UserAuth userAuth = userAuthMapper.selectByPrimaryKey(uid);
            if (userAuth == null) {
                continue;
            }
            UserAuth auth = new UserAuth();
            UserAuthHelper.adminDeletedStatus(userAuth);
            auth.setUid(uid);
            auth.setDeleted(userAuth.getDeleted());
            auth.setUpdateTime(System.currentTimeMillis());
            userAuthMapper.updateByPrimaryKeySelective(auth);
        }
        return true;
    }


    public void afterRegLogin(long uid, String password, HttpServletRequest request, HttpServletResponse response) {
        //set auth cookie
        String authcookie = SecureAuthCookie.genSAuthcookie(uid, TimeHelper.currentTimeSeconds(), password, IPHelper.getRemoteAddr(request), null);
        ControllerHelper.setLoginCookie(request, response, authcookie);
    }
}
