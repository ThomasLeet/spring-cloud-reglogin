package com.thomas.user.account.controller;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.api.CommonController;
import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import com.thomas.user.account.dto.LoginVO;
import com.thomas.user.account.dto.RegVO;
import com.thomas.user.account.dto.UpdateProfileVO;
import com.thomas.user.account.service.AccountService;
import com.thomas.user.account.service.ProfileService;
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
import java.util.List;

@Api(tags = "内网-管理员操作，只供admin服务调用")
@RestController
@RequestMapping("/v1/internal/account/")
@Validated
public class AccountInternalController extends CommonController {

    static final Logger logger = LoggerFactory.getLogger(AccountInternalController.class);

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "删除多个用户",notes = "需要申请,只能通过内网访问")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uids", value = "用户uid列表", required = true, dataType = "List")})
    @DeleteMapping(path = "/multiple-delete")
    public BaseResponse<BooleanView> batchDelete(@RequestParam List<Long> uids) {
        accountService.adminDeleteUids(uids);
        return createJsonResponse(ResultCode.SUCCESS, new BooleanView(), null, null);
    }


}
