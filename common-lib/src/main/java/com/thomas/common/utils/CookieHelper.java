package com.thomas.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CookieHelper {

    private static final String COOKIE_DATE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    private static final ThreadLocal<DateFormat> COOKIE_DATE_FORMAT = ThreadLocal.withInitial(() -> {
        DateFormat df = new SimpleDateFormat(COOKIE_DATE_PATTERN, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df;
    });
    private static final String ANCIENT_DATE;

    public static void addCookieWithSameSiteNone(HttpServletResponse response, Cookie cookie) {
        String header = generateHeader(cookie);
        response.addHeader("Set-Cookie", header);
    }

    private static String generateHeader(Cookie cookie) {
        StringBuffer header = new StringBuffer();
        header.append(cookie.getName());
        header.append('=');
        String value = cookie.getValue();
        if (value != null && value.length() > 0) {
            header.append(value);
        }

        int maxAge = cookie.getMaxAge();
        if (maxAge > -1) {
            header.append("; Max-Age=");
            header.append(maxAge);
            header.append("; Expires=");
            if (maxAge == 0) {
                header.append(ANCIENT_DATE);
            } else {
                ((DateFormat)COOKIE_DATE_FORMAT.get()).format(new Date(System.currentTimeMillis() + (long)maxAge * 1000L), header, new FieldPosition(0));
            }
        }

        String domain = cookie.getDomain();
        if (domain != null && domain.length() > 0) {
            header.append("; Domain=");
            header.append(domain);
        }

        String path = cookie.getPath();
        if (path != null && path.length() > 0) {
            header.append("; Path=");
            header.append(path);
        }

        header.append("; Secure");
        if (cookie.isHttpOnly()) {
            header.append("; HttpOnly");
        }

        header.append("; SameSite=None");
        return header.toString();
    }

    static {
        ANCIENT_DATE = ((DateFormat)COOKIE_DATE_FORMAT.get()).format(new Date(10000L));
    }
}
