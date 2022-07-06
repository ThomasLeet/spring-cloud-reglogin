package com.thomas.common;

import com.thomas.common.secure.ServiceToken;

public class ServiceTokenTest {
    public static void main(String[] args) {
        System.out.println("account: "+ServiceToken.encodeToken("account-service"));
        System.out.println("admin: "+ServiceToken.encodeToken("admin-service"));
        System.out.println("profile: "+ServiceToken.encodeToken("profile-service"));
    }
}
