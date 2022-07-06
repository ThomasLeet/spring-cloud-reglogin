package com.thomas.user.auth.controller;

import com.thomas.user.auth.dto.AuthCookieContext;
import com.thomas.user.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;


@RestController
@RequestMapping("/v1/auth")
@Validated
public class AuthController {

    static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;
    @GetMapping(path = "/generate-cookie")
    public String generateCookie(@RequestParam AuthCookieContext context) {
        return authService.genAuthcookie(context);
    }

}
