package com.thomas.user.admin.controller;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.CommonController;
import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.admin.dto.AdminLoginVO;
import com.thomas.user.admin.dto.UserAdminView;
import com.thomas.user.admin.model.UserAdmin;
import com.thomas.user.admin.service.AdminService;
import com.thomas.user.admin.utils.AdminVOHelper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "内网-管理员操作必须经过Gateway")
@RestController
@RequestMapping("/v1/admin/")
@Validated
public class AdminController extends CommonController {

    static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @ApiOperation(value = "管理员删除", notes = "管理员需登录态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Admin-User-ID", value = "管理员登录后通过gateway鉴权结果", required = true, paramType = "Header"),
            @ApiImplicitParam(name = "uids", value = "要删除的用户列表", required = true, dataType = "List")})
    @DeleteMapping(path = "/multiple-delete")
    public BaseResponse<BooleanView> batchDelete(@RequestHeader(value = UserHeaders.ADMIN_UID) Long uid, @RequestParam List<Long> uids
            , HttpServletRequest request, HttpServletResponse response) {
        if (CollectionUtils.isEmpty(uids)) {
            return createJsonResponse(ResultCode.PARAM_VALID_ERROR, new BooleanView(false), null, null);
        }
        adminService.batchDelete(uids);
        return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
    }

    @ApiOperation(value = "管理员登录")
    @PostMapping(path = "/login")
    public BaseResponse<UserAdminView> login(@RequestBody @Validated AdminLoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        if (loginVO == null) {
            return createJsonResponse(ResultCode.PARAM_TYPE_ERROR, null, null, null);
        }
        UserAdmin admin = adminService.login(loginVO, request, response);
        return createJsonResponse(ResultCode.SUCCESS, AdminVOHelper.convertToView(admin), null, null);
    }

}
