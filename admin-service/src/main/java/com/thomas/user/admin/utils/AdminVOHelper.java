package com.thomas.user.admin.utils;

import com.thomas.user.admin.dto.UserAdminView;
import com.thomas.user.admin.model.UserAdmin;

public class AdminVOHelper {

    public static UserAdminView convertToView(UserAdmin userAdmin){
        if(userAdmin == null){
            return null;
        }
        UserAdminView view = new UserAdminView();
        if(userAdmin.getName() != null){
            view.setName(userAdmin.getName());
        }
        return  view;
    }
}
