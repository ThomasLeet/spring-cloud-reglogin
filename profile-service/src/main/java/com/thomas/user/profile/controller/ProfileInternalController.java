package com.thomas.user.profile.controller;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.CommonController;
import com.thomas.common.api.ResultCode;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.profile.dto.ProfileVO;
import com.thomas.user.profile.dto.ProfileVO.Create;
import com.thomas.user.profile.dto.ProfileVO.Update;
import com.thomas.user.profile.model.UserProfile;
import com.thomas.user.profile.service.ProfileService;
import com.thomas.user.profile.utils.UserProfileHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "内网-用户信息操作，只供account、admin服务调用")
@RestController
@RequestMapping("/v1/internal/profile")
@Validated
public class ProfileInternalController extends CommonController {

    static final Logger logger = LoggerFactory.getLogger(ProfileInternalController.class);

    @Autowired
    ProfileService profileService;
    @ApiOperation(value = "获取用户信息",notes = "需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "Long")})
    @GetMapping(path = "/get/{uid}")
    public BaseResponse<ProfileVO> getUid(@PathVariable("uid") Long uid) {
        UserProfile userProfile = profileService.getByUid(uid,false);
        return createJsonResponse(ResultCode.SUCCESS, UserProfileHelper.convertToVO(userProfile), null, null);
    }

    @ApiOperation(value = "创建用户信息",notes = "需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @PostMapping(path = "/create")
    public BaseResponse<ProfileVO> create(@RequestBody @Validated({Create.class}) ProfileVO profile) {
        UserProfile userProfile = UserProfileHelper.convertToUserProfile(profile);
        profileService.insert(userProfile);
        UserProfile rst = profileService.getByUid(userProfile.getUid(),false);
        return createJsonResponse(ResultCode.SUCCESS, UserProfileHelper.convertToVO(rst), null, null);
    }

    @ApiOperation(value = "更新用户信息",notes = "需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @PutMapping(path = "/update")
    public BaseResponse<ProfileVO> update(@RequestBody @Validated({Update.class}) ProfileVO profile) {
        int rows = profileService.update(UserProfileHelper.convertToUserProfile(profile));
        UserProfile rst = profileService.getByUid(profile.getUid(),false);
        return createJsonResponse(ResultCode.SUCCESS, UserProfileHelper.convertToVO(rst), null, null);
    }

    @ApiOperation(value = "普通删除用户", notes = "需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "Long")})
    @DeleteMapping(path = "/delete")
    public BaseResponse<BooleanView> delete(@RequestParam Long uid) {
        profileService.delete(uid);
        return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
    }

    @ApiOperation(value = "删除多个用户",notes = "管理员删除后，用户无法再自己激活。需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "uids", value = "用户uid列表", required = true, dataType = "List")})
    @DeleteMapping(path = "/multiple-delete")
    public BaseResponse<BooleanView> batchDelete(@RequestParam List<Long> uids) {
        if(CollectionUtils.isEmpty(uids)){
            return createJsonResponse(ResultCode.PARAM_VALID_ERROR, new BooleanView(true), null, null);
        }
        profileService.deleteByUids(uids);
        return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
    }

}
