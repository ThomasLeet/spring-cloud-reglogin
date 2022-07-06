package com.thomas.user.profile.dto;

import com.thomas.common.api.ResultCode;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalBaseRespose {
    private String message;
    @Builder.Default
    private int code = ResultCode.SUCCESS.code;
    private String type;
    public boolean isSuccess() {
        return code == ResultCode.SUCCESS.code;
    }
}
