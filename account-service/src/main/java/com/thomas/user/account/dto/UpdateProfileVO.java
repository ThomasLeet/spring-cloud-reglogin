package com.thomas.user.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@ApiModel(description = "更新实体")
@Getter
@Setter
public class UpdateProfileVO {

    @ApiModelProperty(hidden = true)
    private Long uid;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "旧密码")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;

    @ApiModelProperty(value = "确认新密码")
    private String confirmPwd;
}
