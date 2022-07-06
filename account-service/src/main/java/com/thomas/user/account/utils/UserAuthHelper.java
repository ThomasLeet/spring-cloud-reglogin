package com.thomas.user.account.utils;

import com.alibaba.fastjson.JSON;
import com.thomas.user.account.model.UserAuth;

public class UserAuthHelper {

    public static void userDeletedStatus(UserAuth userAuth) {
        if (userAuth.getDeleted() == null) {
            userAuth.setDeleted(DeleteStatus.USER_DELETE);
        } else {
            byte deleted = (byte) (userAuth.getDeleted() | DeleteStatus.USER_DELETE);
            userAuth.setDeleted(deleted);
        }
    }

    public static void adminDeletedStatus(UserAuth userAuth) {
        if (userAuth.getDeleted() == null) {
            userAuth.setDeleted(DeleteStatus.ADMIN_DELETE);
        } else {
            byte deleted = (byte) (userAuth.getDeleted() | DeleteStatus.ADMIN_DELETE);
            userAuth.setDeleted(deleted);
        }
    }

    public static boolean isUserDeleted(Byte status) {
        if (status == null) {
            return false;
        }
        return (status.byteValue() & DeleteStatus.USER_DELETE) == DeleteStatus.USER_DELETE;
    }

    public static boolean isAdminDeleted(Byte status) {
        if (status == null) {
            return false;
        }
        return (status.byteValue() & DeleteStatus.ADMIN_DELETE) == DeleteStatus.ADMIN_DELETE;
    }

    public static boolean isDeleted(UserAuth userAuth) {
        if (userAuth == null) {
            return false;
        }
        return isUserDeleted(userAuth.getDeleted()) || isAdminDeleted(userAuth.getDeleted());
    }

    public static boolean isActivated(UserAuth userAuth) {
        if (userAuth == null) {
            return false;
        }
        return userAuth.getActivated() != null && userAuth.getActivated() ? true : false;
    }

}
