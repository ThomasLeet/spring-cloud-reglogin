package com.thomas.user.account.utils;

import com.thomas.common.utils.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ControllerHelper {
	private static final Logger logger = LoggerFactory.getLogger(ControllerHelper.class);
	
	private static final int MIN_BIRTH_YEAR = 1920;
	

	public static final int THREE_MONTH = 3600 * 24 * 90;
	
	public static void setLoginCookie(HttpServletRequest request, HttpServletResponse response, String authcookie){
		setLoginCookie(request, response, authcookie, null);
	}
	
	
	public static void setLoginCookie(HttpServletRequest request, HttpServletResponse response, String authcookie, Integer age){
		ResponseHelper.setLoginCookieHttpOnly(request, response, authcookie, age);
	}

}
