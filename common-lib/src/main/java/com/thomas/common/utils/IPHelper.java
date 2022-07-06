package com.thomas.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class IPHelper {

    static final Logger logger = LoggerFactory.getLogger(IPHelper.class);

    public static int ip2int(String ip) {
        if (ip == null) {
            return 0;
        } else {
            InetAddress address;
            try {
                address = InetAddress.getByName(ip);
            } catch (UnknownHostException var8) {
                return 0;
            }

            byte[] bytes = address.getAddress();
            int a = 255 & bytes[0];
            int b = 255 & bytes[1];
            int c = 255 & bytes[2];
            int d = 255 & bytes[3];
            int result = a << 24 | b << 16 | c << 8 | d;
            return result;
        }
    }

    public static String int2ip(int ip) {
        int a = 255 & ip >> 24;
        int b = 255 & ip >> 16;
        int c = 255 & ip >> 8;
        int d = 255 & ip >> 0;
        return (new StringBuilder(20)).append(a).append(".").append(b).append(".").append(c).append(".").append(d).toString();
    }

    public static boolean isIpv6Address(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        } else {
            InetAddress address = null;

            try {
                address = InetAddress.getByName(ip);
            } catch (UnknownHostException var3) {
            }

            return address instanceof Inet6Address;
        }
    }

    public static String getRemoteAddr(HttpServletRequest req) {
        if (req == null) {
            return "127.0.0.1";
        }
        String ip = _getRemoteAddr(req);
        if (isIpv6Address(ip)) {
            logger.info("find ipv6 access, ip={}, url={}", ip, req.getRequestURI());
        }
        return ip;
    }

    private static String _getRemoteAddr(HttpServletRequest req) {
        //先取remote ip，如果不是内网ip则直接返回
        String ip = req.getRemoteAddr();
        if (isIpv6Address(ip) || (StringUtils.isNotBlank(ip) && ip.indexOf('.') > 0)) {
            return ip;
        }
        //get ip from Reverse Proxy
        try {
            ip = req.getHeader("x-real-ip");
            if (isIPAddrSimply(ip) || isIpv6Address(ip)) {
                return ip;
            }
            ip = req.getHeader("X-Forwarded-For");
            if (org.apache.commons.lang.StringUtils.isNotBlank(ip)) {
                String[] ips = org.apache.commons.lang.StringUtils.split(ip, ',');
                if (ips != null) {
                    for (String tmpip : ips) {
                        tmpip = tmpip.trim();
                        if (isIPAddrSimply(tmpip) || isIpv6Address(ip)) {
                            return tmpip.trim();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("get remote ip address error", ex);
        }
        //还是取不到则取内网ip
        ip = req.getRemoteAddr();
        if (ip == null || ip.indexOf('.') == -1) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static final Pattern IP_SIMPLY_PATTERN = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");

    public static boolean isIPAddrSimply(String addr) {
        if (org.apache.commons.lang.StringUtils.isEmpty(addr)) {
            return false;
        }
        if (!IP_SIMPLY_PATTERN.matcher(addr).matches()) {
            return false;
        }
        return true;
    }
}
