package com.thomas.common.api;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "code码")
    @Builder.Default
    private int code = ResultCode.SUCCESS.code;
    @ApiModelProperty(value = "成功、错误类型")
    private String type;
    @ApiModelProperty(value = "返回数据体")
    private T data;
    @ApiModelProperty(value = "是否操作成功")
    public boolean isSuccess() {
        return code == ResultCode.SUCCESS.code;
    }
}
