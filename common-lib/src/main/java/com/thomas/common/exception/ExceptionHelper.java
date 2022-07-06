package com.thomas.common.exception;

import com.thomas.common.api.ResultCode;

public class ExceptionHelper {

    public static UserBizException createUserBizException(ResultCode code) {
        if (code == null) {
            code = ResultCode.FAILURE;
        }
        return new UserBizException(code.getCode(), code.getMsg(), code.getType());
    }

    public static AnnotationException createAnnotationException(ResultCode code,String message) {
        if (code == null) {
            code = ResultCode.FAILURE;
        }
        return new AnnotationException(code.getCode(), message, code.getType());
    }

}
