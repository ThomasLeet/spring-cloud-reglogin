package com.thomas.user.account.controller;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.CommonController;
import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.account.dto.LoginVO;
import com.thomas.user.account.dto.ProfileView;
import com.thomas.user.account.dto.RegVO;
import com.thomas.user.account.dto.UpdateProfileVO;
import com.thomas.user.account.service.AccountService;
import com.thomas.user.account.utils.UpdateProfileHelper;
import com.thomas.user.account.validation.PhoneNumberHelper;
import com.thomas.user.profile.dto.ProfileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

@Api(tags = "外网-用户登录、注册、更新、删除")
@RestController
@RequestMapping("/v1/account")
@Validated
public class AccountController extends CommonController {

    static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "用户登录", notes = "手机号登录countryCode和phone必填")
    @PostMapping(path = "/login")
    public BaseResponse<ProfileView> login(HttpServletRequest request, HttpServletResponse response,@RequestBody @Validated LoginVO loginVO) {
        if (loginVO == null) {
            return createJsonResponse(ResultCode.PARAM_TYPE_ERROR, null, null, null);
        }
        ProfileVO profileVO = accountService.login(loginVO, request, response);
        return createJsonResponse(ResultCode.SUCCESS, UpdateProfileHelper.convertToView(profileVO), null, null);
    }

    @ApiOperation(value = "注册", notes = "regType为必填项，手机号登录countryCode和phone必填。手机号省略了手机验证码功能，注册后直接激活")
    @PostMapping(path = "/register")
    public BaseResponse<ProfileView> register(HttpServletRequest request, HttpServletResponse response,@RequestBody @Validated RegVO regVo) {
        if (regVo == null) {
            return createJsonResponse(ResultCode.PARAM_TYPE_ERROR, null, null, null);
        }
        if (regVo.isPhoneRegister() && !PhoneNumberHelper.isValidNumber(regVo.getCountryCode(), regVo.getPhone())) {
            return createJsonResponse(ResultCode.PARAM_PHONE_INVALID, null, ResultCode.PARAM_PHONE_INVALID.msg, null);
        }
        ProfileVO vo = accountService.register(regVo, request, response);
        return createJsonResponse(ResultCode.SUCCESS, UpdateProfileHelper.convertToView(vo), null, null);
    }

    @ApiOperation(value = "更新", notes = "需要登录态（登录后Cookie中A0001），昵称、头像、密码可以单独更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "A0001", value = "用户登录后Cookie", paramType = "Header", dataType = "String"),
            @ApiImplicitParam(name = "X-User-ID", value = "gateway 设置，调用方忽略", paramType = "Header")})
    @PutMapping(path = "/update")
    public BaseResponse<ProfileView> update(@RequestHeader(value = UserHeaders.UID) Long uid, @RequestBody @Validated UpdateProfileVO profile) {
        if (profile == null) {
            return createJsonResponse(ResultCode.PARAM_TYPE_ERROR, null, null, null);
        }
        profile.setUid(uid);
        ProfileVO profileVO = accountService.update(profile);
        return createJsonResponse(ResultCode.SUCCESS, UpdateProfileHelper.convertToView(profileVO), null, null);
    }

    @ApiOperation(value = "用户自己删除", notes = "需要登录态（登录后Cookie中A0001）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "A0001", value = "用户登录后Cookie", required = true, paramType = "Header"),
            @ApiImplicitParam(name = "X-User-ID", value = "gateway 设置，调用方忽略", required = true, paramType = "Header")})
    @DeleteMapping(path = "/delete")
    public BaseResponse<BooleanView> delete(@RequestHeader(value = UserHeaders.UID) Long uid) {
        accountService.delete(uid);
        return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
    }

    @ApiOperation("邮箱激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "邮件链接中code参数", required = true, dataType = "String")})
    @GetMapping(path = "/activate")
    public BaseResponse<BooleanView> activate(@RequestParam String code) {
        boolean activate = accountService.activate(code);
        if (activate) {
            return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
        }
        return createJsonResponse(ResultCode.FAILURE, new BooleanView(false), null, null);
    }

    @ApiOperation("重发邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮件地址", required = true, dataType = "String")})
    @PutMapping(path = "/send-email")
    public BaseResponse<BooleanView> email(@RequestParam @Email String email) {
        boolean success = accountService.sendEmail(email);
        if (success) {
            return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
        }
        return createJsonResponse(ResultCode.FAILURE, new BooleanView(false), null, null);
    }

}
