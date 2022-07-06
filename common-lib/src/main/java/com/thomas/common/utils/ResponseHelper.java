package com.thomas.common.utils;

import com.thomas.common.constants.UserCookie;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ResponseHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ResponseHelper.class);
	
	public static final int THREE_MONTH = 3600 * 24 * 90;
	public static final int ONE_HOUR = 3600;

	public static void setLoginCookieHttpOnly(HttpServletRequest request, HttpServletResponse response, String authcookie, Integer age){
		if(age == null){
			age = THREE_MONTH;
		}
		RequestHelper.setHttpOnlyCookie(request, response, UserCookie.AUTH, authcookie,  age);
	}

	public static void setAdminLoginCookieHttpOnly(HttpServletRequest request, HttpServletResponse response, String authcookie, Integer age){
		if(age == null){
			age = ONE_HOUR;
		}
		RequestHelper.setHttpOnlyCookie(request, response, UserCookie.ADMIN_AUTH, authcookie,  age);
	}


    public static String cleanXSS(String value) {
    	if(StringUtils.isBlank(value)) {
    		return value;
    	}
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");
		value = value.replaceAll(";", "");
		return value;
	}

}
