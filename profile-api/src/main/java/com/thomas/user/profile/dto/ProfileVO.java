package com.thomas.user.profile.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "用户信息实体")
@Getter
@Setter
public class ProfileVO {

    @ApiModelProperty(value = "用户uid")
    @NotNull(groups = {Create.class,Update.class,Delete.class} ,message = "Uid is null")
    private Long uid;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "是否已删除")
    @NotNull(groups = {Create.class,Delete.class} ,message = "Deleted is null")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "是否激活")
    @NotNull(groups = {Create.class} ,message = "Activated is null")
    private Boolean activated;

    public interface Create{}
    public interface Update{}
    public interface Delete{}
}
