package com.thomas.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
public enum ResultCode {
    //Common Error
    SUCCESS(HttpServletResponse.SC_OK, ErrorType.OK, "Operation is Successful"),

    FAILURE(HttpServletResponse.SC_BAD_REQUEST, ErrorType.UNKNOWN, "Operation fail"),

    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, ErrorType.UN_AUTHORIZED, "Request Unauthorized"),

    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, ErrorType.NOT_FOUND, "404 Not Found"),

    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, ErrorType.MSG_NOT_READABLE, "Message Can't be Read"),

    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ErrorType.METHOD_NOT_SUPPORTED, "Method Not Supported"),

    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, ErrorType.MEDIA_TYPE_NOT_SUPPORTED, "Media Type Not Supported"),

    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorType.UNKNOWN, "Internal Server Error"),

    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PARAMETER_ERROR, "Missing Required Parameter"),

    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PARAMETER_ERROR, "Parameter Type Mismatch"),

    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PARAMETER_ERROR, "Parameter Binding Error"),

    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PARAMETER_ERROR, "Parameter Validation Error"),

    PARAM_PHONE_INVALID(HttpServletResponse.SC_BAD_REQUEST, ErrorType.INVALID_PHONE, "Parameter Validation Error"),

    //Biz Error
    EMAIL_CONFLICT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.EMAIL_CONFLICT, "The mail already exists"),

    NICKNAME_CONFLICT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.NICKNAME_CONFLICT, "The nickname already exists"),

    PHONE_CONFLICT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PHONE_CONFLICT, "The Phone already exists"),

    EMAIL_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, ErrorType.EMAIL_NOT_EXIST, "The mail does not exist"),

    NICKNAME_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, ErrorType.NICKNAME_NOT_EXIST, "The nickname does not exist"),

    PHONE_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PHONE_NOT_EXIST, "The Phone does not exist"),

    USER_EXIST(HttpServletResponse.SC_BAD_REQUEST, ErrorType.USER_EXIST, "User already exist"),

    USER_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, ErrorType.USER_NOT_EXIST, "User does not exist"),

    PASSWORD_INCORRECT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PASSWORD_INCORRECT, "passpord incorrect"),

    PASSWORD_INCONSISTENT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PASSWORD_INCONSISTENT, "The two passwords are inconsistent"),

    PASSWORD_INCORRECT_FORMAT(HttpServletResponse.SC_BAD_REQUEST, ErrorType.PASSWORD_INCORRECT_FORMAT, "Incorrect password format"),

    SERVICE_DEGRADE(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorType.SERVICE_DEGRADE, "request  fail"),

    EMAIL_LINK_EXPIRED(HttpServletResponse.SC_BAD_REQUEST, ErrorType.EMAIL_LINK_EXPIRED, "The validation link has expired"),

    USER_BE_FROZEN(HttpServletResponse.SC_BAD_REQUEST, ErrorType.USER_BE_FROZEN, "The user is frozen"),

    EMAIL_NOT_ACTIVATED(HttpServletResponse.SC_BAD_REQUEST, ErrorType.EMAIL_NOT_ACTIVATED, "EMail is not activated.");


    public final int code;

    public final String type;

    public final String msg;
    }
