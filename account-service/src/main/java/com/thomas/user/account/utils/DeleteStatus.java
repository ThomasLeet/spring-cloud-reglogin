package com.thomas.user.account.utils;

public class DeleteStatus {
    public static final byte DEFAULT_STATUS = (byte) 0;
    public static final byte USER_DELETE = (byte) 1;
    public static final byte ADMIN_DELETE = (byte) 1 << 1;
}
