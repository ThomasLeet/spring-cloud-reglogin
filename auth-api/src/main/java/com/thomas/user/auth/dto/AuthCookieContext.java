package com.thomas.user.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCookieContext {
    private Long uid;
    private String password;
    private int ctime;
    private String ip;
}
