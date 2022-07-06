package com.thomas.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);
    
    private static String PREFIX = "\\u";
    
    private static int EXPIRE = 86400;

    public static final String DOMAIN = "127.0.0.1";//".baidu.com";

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private RequestHelper() {
    }

    /**
     * Convenience method to set a cookie
     *
     * @param response the current response
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path to set it on
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
								 String path, String domain, int maxAge) {
    	setCookie(request, response, name, value, path, domain, maxAge, false, false);
    }

	public static void setHttpOnlyCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(request, response, name, value, "/", getCookieDomain(request), maxAge, false, true);
	}


    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
								 String path, String domain, int maxAge, boolean isSecure, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setVersion(0);
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath(path);
        
        cookie.setMaxAge(maxAge); // Sets the maximum age in seconds for this Cookie.
		if (request != null && StringUtils.equals("https", request.getHeader("X-Scheme"))) {
			CookieHelper.addCookieWithSameSiteNone(response, cookie);
		} else {
			response.addCookie(cookie);
		}
    }

    public static String getReferer(HttpServletRequest request) {
    	return request.getHeader("Referer");
    }


    public static String getCookieDomain(HttpServletRequest request){
    	return DOMAIN;
    }
    
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(request, response, name, value, "/", getCookieDomain(request), maxAge);
    }

    public static String getDeviceId(HttpServletRequest request) {
        String deviceId = request.getParameter("device_id");
        if(StringUtils.isNotBlank(deviceId)) {
            return deviceId;
        }
        return request.getHeader("X-Mobile-Device");
    }

    public static void deleteCookie(HttpServletResponse response,
                                    Cookie cookie, String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

}
