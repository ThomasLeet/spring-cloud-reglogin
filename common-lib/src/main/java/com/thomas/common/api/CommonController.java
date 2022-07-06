package com.thomas.common.api;

import com.thomas.common.exception.AnnotationException;
import com.thomas.common.exception.UserBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class CommonController {

    static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    public <T> BaseResponse<T> createJsonResponse(ResultCode code, T data, String msg, HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        response.setCode(code.getCode());
        response.setType(code.getType());
        response.setMessage(msg != null ? msg : code.getMsg());
        response.setData(data);
        return response;
    }

    public <T> BaseResponse<T> createJsonResponse(Integer code, String type, T data, String msg, HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setType(type);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleException(HttpServletRequest request,
                                        Exception ex,
                                        HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        return this.createJsonResponse(ResultCode.INTERNAL_SERVER_ERROR, null, ResultCode.INTERNAL_SERVER_ERROR.msg, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleIllegalArgumentException(HttpServletRequest request,
                                                       IllegalArgumentException ex,
                                                       HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        return this.createJsonResponse(ResultCode.PARAM_VALID_ERROR, null, ResultCode.PARAM_VALID_ERROR.msg, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleIllegalStateException(HttpServletRequest request,
                                                    IllegalArgumentException ex,
                                                    HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        return this.createJsonResponse(ResultCode.INTERNAL_SERVER_ERROR, null, ResultCode.INTERNAL_SERVER_ERROR.msg, request);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleBindException(HttpServletRequest request,
                                            BindException ex,
                                            HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        FieldError error = ex.getFieldError();
        return this.createJsonResponse(ResultCode.PARAM_VALID_ERROR, null, error == null ? ResultCode.PARAM_VALID_ERROR.msg : error.getDefaultMessage(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleConstraintViolationException(HttpServletRequest request,
                                                           ConstraintViolationException ex,
                                                           HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StringBuilder msgBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
        }
        String errorMessage = msgBuilder.toString();
        if (errorMessage.length() > 1) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        return this.createJsonResponse(ResultCode.PARAM_VALID_ERROR, null, errorMessage, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleMethodArgumentNotValidException(HttpServletRequest request,
                                                              MethodArgumentNotValidException ex,
                                                              HttpServletResponse response) {
        logger.error("INTERNAL_SERVER_ERROR", ex);
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        StringBuilder msgBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
        }
        String errorMessage = msgBuilder.toString();
        if (errorMessage.length() > 1) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        return this.createJsonResponse(ResultCode.PARAM_VALID_ERROR, null, errorMessage, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse handleError(MethodArgumentTypeMismatchException e) {
        logger.warn("Method Argument Type Mismatch", e);
        String message = String.format("Method Argument Type Mismatch: %s", e.getName());
        return this.createJsonResponse(ResultCode.PARAM_VALID_ERROR, null, message, null);
    }

    @ExceptionHandler(UserBizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleUserBizException(HttpServletRequest request,
                                               UserBizException ex,
                                               HttpServletResponse response) {
        return this.createJsonResponse(ex.getCode(), ex.getType(), null, ex.getMessage(), request);
    }

    @ExceptionHandler(AnnotationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleAAnnotationException(HttpServletRequest request,
                                                   AnnotationException ex,
                                                   HttpServletResponse response) {
        return this.createJsonResponse(ex.getCode(), ex.getType(), null, ex.getMessage(), request);
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    MissingRequestHeaderException e,
                                    HttpServletResponse response) {
        logger.error("miss header", e);
        return createJsonResponse(ResultCode.UN_AUTHORIZED, null, null, request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    NoHandlerFoundException e,
                                    HttpServletResponse response) {
        logger.error("404 Not Found", e);
        return createJsonResponse(ResultCode.NOT_FOUND, null, null, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    HttpMessageNotReadableException ex,
                                    HttpServletResponse response) {
        logger.error("Message Not Readable", ex);
        return createJsonResponse(ResultCode.MSG_NOT_READABLE, null, null, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    HttpRequestMethodNotSupportedException e,
                                    HttpServletResponse response) {
        logger.error("Request Method Not Supported", e);
        return createJsonResponse(ResultCode.METHOD_NOT_SUPPORTED, null, null, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    HttpMediaTypeNotSupportedException e,
                                    HttpServletResponse response) {
        logger.error("Media Type Not Supported", e);
        return createJsonResponse(ResultCode.MEDIA_TYPE_NOT_SUPPORTED, null, null, request);
    }


    @ExceptionHandler(Throwable.class)
    public BaseResponse handleError(HttpServletRequest request,
                                    Throwable e,
                                    HttpServletResponse response) {
        logger.error("Internal Server Error", e);
        return createJsonResponse(ResultCode.INTERNAL_SERVER_ERROR, null, null, request);
    }

}
