package com.thomas.user.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "用户信息实体")
@Getter
@Setter
public class ProfileView {

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
