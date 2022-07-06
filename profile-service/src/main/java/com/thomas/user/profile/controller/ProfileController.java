package com.thomas.user.profile.controller;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.CommonController;
import com.thomas.common.api.ResultCode;
import com.thomas.user.profile.dto.GenericProfileResponse;
import com.thomas.user.profile.dto.ProfileVO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "外网-用户信息")
@RestController
@RequestMapping("/v1/profile")
@Validated
public class ProfileController extends CommonController {

    static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    ProfileService profileService;

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "Long")})
    @GetMapping(path = "/get/{uid}")
    public BaseResponse<ProfileVO> getUid(@PathVariable("uid") Long uid) {
        UserProfile userProfile = profileService.getByUid(uid, true);
        return createJsonResponse(ResultCode.SUCCESS, UserProfileHelper.convertToVO(userProfile), null, null);
    }

}
