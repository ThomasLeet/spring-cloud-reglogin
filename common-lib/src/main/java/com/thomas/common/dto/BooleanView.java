package com.thomas.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "操作结果")
@Getter
@Setter
public class BooleanView {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    public BooleanView() {
        this.success = true;
    }

    public BooleanView(Boolean success) {
        this.success = success;
    }
}
