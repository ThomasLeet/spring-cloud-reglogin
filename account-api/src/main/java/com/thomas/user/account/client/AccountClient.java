package com.thomas.user.account.client;

import com.thomas.common.api.BaseResponse;
import com.thomas.common.constants.ServiceConstants;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.dto.BooleanView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ServiceConstants.ACOCUNT_SERVICE_NAME)
@Component
@RequestMapping(value = "/v1/internal/account",
        headers = {UserHeaders.AGENT_TYPE + "=${user.header.agent_type}", UserHeaders.USER_TOKEN + "=${user.header.token}"})
public interface AccountClient {
    @DeleteMapping(path = "/multiple-delete")
    public BaseResponse<BooleanView> batchDelete(@RequestParam List<Long> uids);

}
