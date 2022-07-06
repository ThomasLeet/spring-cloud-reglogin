package com.thomas.user.profile.client;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.constants.ServiceConstants;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.profile.dto.ProfileVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = ServiceConstants.PROFILE_SERVICE_NAME, fallback = ProfileDegradeClient.class)
@Component
@RequestMapping(value = "/v1/internal/profile",
        headers = {UserHeaders.AGENT_TYPE + "=${user.header.agent_type}", UserHeaders.USER_TOKEN + "=${user.header.token}"})
public interface ProfileClient {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @GetMapping(path = "/get/{uid}")
    BaseResponse<ProfileVO> getInternalUid(@PathVariable("uid") Long uid);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @PostMapping(path = "/create")
    BaseResponse<ProfileVO> create(@RequestBody @Validated({ProfileVO.Create.class}) ProfileVO profile);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @PutMapping(path = "/update")
    BaseResponse<ProfileVO> update(@RequestBody @Validated({ProfileVO.Update.class}) ProfileVO profile);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @DeleteMapping(path = "/delete")
    BaseResponse<BooleanView> delete(@RequestParam Long uid);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Agent-Type", value = "内网调用方标识，联系管理员获取", required = true, paramType = "Header",dataType = "String"),
            @ApiImplicitParam(name = "X-User-Token", value = "内网调用方token，联系管理员获取", required = true, paramType = "Header",dataType = "String")})
    @DeleteMapping(path = "/multiple-delete")
    BaseResponse<BooleanView> batchDelete(@RequestParam List<Long> uids);
}
