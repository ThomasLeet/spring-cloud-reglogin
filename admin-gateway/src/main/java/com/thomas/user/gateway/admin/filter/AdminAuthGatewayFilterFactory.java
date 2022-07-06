package com.thomas.user.gateway.admin.filter;

import com.thomas.common.constants.UserConstants;
import com.thomas.common.constants.UserCookie;
import com.thomas.common.constants.UserHeaders;
import com.thomas.common.secure.AdminSecureAuthCookie;
import com.thomas.common.utils.TimeHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class AdminAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AdminAuthGatewayFilterFactory.Config> {

    public AdminAuthGatewayFilterFactory() {
        super(Config.class);
    }

    public AdminAuthGatewayFilterFactory(Class<Config> configClass) {
        super(configClass);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            String authCookieStr = null;
            if (cookies != null || !cookies.isEmpty()) {
                //cookie
                Map<String, HttpCookie> cookieMap = cookies.toSingleValueMap();
                HttpCookie authCookie = cookieMap.get(UserCookie.ADMIN_AUTH);
                if (authCookie != null) {
                    authCookieStr = authCookie.getValue();
                } else {
                    //header
                    HttpHeaders headers = request.getHeaders();
                    String authHeader = headers.getFirst(UserHeaders.FRONT_END_ADMIN_AUTH);
                    if(!StringUtils.isEmpty(authHeader)){
                        authCookieStr = authHeader;
                    }else{
                        //query
                        MultiValueMap<String, String> queryParams = request.getQueryParams();
                        if (queryParams != null || !queryParams.isEmpty()) {
                            List<String> list = queryParams.get(UserConstants.PARAMETER_AUTH_COOKIE);
                            if (!CollectionUtils.isEmpty(list)) {
                                authCookieStr = list.get(0);
                            }
                        }
                    }

                }
            }
            if(authCookieStr == null){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            ServerWebExchange serverWebExchange = null;
            try {
                AdminSecureAuthCookie secureAuthCookie = AdminSecureAuthCookie.decodeAuthCookie(authCookieStr);
                if(secureAuthCookie == null || TimeHelper.currentTimeSeconds() > secureAuthCookie.expire){
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                ServerHttpRequest authRequest = request.mutate().header(UserHeaders.ADMIN_UID, secureAuthCookie.uid + "").build();
                serverWebExchange = exchange.mutate().request(authRequest).build();
            } catch (Exception e) {
                serverWebExchange = exchange;
            }
            return  chain.filter(serverWebExchange);
        };

    }

    public static class Config {
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
