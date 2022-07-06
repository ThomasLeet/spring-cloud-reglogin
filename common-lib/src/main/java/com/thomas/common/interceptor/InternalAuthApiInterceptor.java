package com.thomas.common.interceptor;

import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.common.secure.ServiceToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class InternalAuthApiInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(InternalAuthApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String agentType = request.getHeader(UserHeaders.AGENT_TYPE);
        String token = request.getHeader(UserHeaders.USER_TOKEN);
        if (StringUtils.isBlank(agentType) || StringUtils.isBlank(token)
                || !ServiceToken.checkToken(agentType, token)) {
            throw ExceptionHelper.createUserBizException(ResultCode.UN_AUTHORIZED);
        }
        return super.preHandle(request, response, handler);
    }

    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("");
    }

    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

}
