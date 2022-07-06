package com.thomas.common.exception;

public class UserBizException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private String type;
    private String redirectUrl;

    public UserBizException(Integer code, String message, String redirectUrl, String type) {
        super(message);
        this.code = code;
        this.message = message;
        this.redirectUrl = redirectUrl;
        this.type = type;
    }

    public UserBizException(Integer code, String message, String type) {
        super(message);
        this.code = code;
        this.message = message;
        this.type = type;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
