package com.thomas.user.account.validation;

import com.thomas.user.account.dto.LoginVO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class LoginProvider implements DefaultGroupSequenceProvider<LoginVO> {

    @Override
    public List<Class<?>> getValidationGroups(LoginVO loginVO) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(LoginVO.class);

        if (loginVO != null) {
            if(StringUtils.isNotEmpty(loginVO.getEmail())){
                defaultGroupSequence.add(LoginVO.EmailGroup.class);
            }else{
                defaultGroupSequence.add(LoginVO.PhoneGroup.class);
            }
        }
        return defaultGroupSequence;
    }
}
