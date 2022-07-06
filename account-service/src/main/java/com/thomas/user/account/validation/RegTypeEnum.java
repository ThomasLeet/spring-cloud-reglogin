package com.thomas.user.account.validation;

public enum RegTypeEnum {
    email("e"),phone("p");


    private RegTypeEnum(String type) {
        this.type = type;
    }
    private String type;

    public String getType() {
        return type;
    }

    public static boolean isInEnum(String code) {
        if(code == null || code.length() == 0){
            return false;
        }
        for (RegTypeEnum regType : RegTypeEnum.values()) {
            if (regType.getType().equals(code)){
                return true;
            }
        }
        return false;
    }
}
