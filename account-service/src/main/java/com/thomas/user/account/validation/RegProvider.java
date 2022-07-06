package com.thomas.user.account.validation;

import com.thomas.common.api.ResultCode;
import com.thomas.common.constants.ServiceConstants;
import com.thomas.common.exception.ExceptionHelper;
import com.thomas.user.account.dto.RegVO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class RegProvider implements DefaultGroupSequenceProvider<RegVO> {
    @Override
    public List<Class<?>> getValidationGroups(RegVO regVo) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(RegVO.class);
        if (regVo != null) { // 这块判空请务必要做
            String regType = regVo.getRegType();
            if(StringUtils.isEmpty(regType)){
                throw ExceptionHelper.createAnnotationException(ResultCode.PARAM_VALID_ERROR,"Invalid register type");
            }else if(ServiceConstants.ACCOUNT_EMAIL_REGISTER.equals(regType)){
                defaultGroupSequence.add(RegVO.EmailGroup.class);
            }else{
                defaultGroupSequence.add(RegVO.PhoneGroup.class);
            }
        }
        return defaultGroupSequence;
    }
}
