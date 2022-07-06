package com.thomas.user.profile.dto;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericProfileResponse extends InternalBaseRespose {

    public GenericProfileResponse(String message, int code, String type, ProfileVO data) {
        super(message, code, type);
        this.data = data;
    }

    public GenericProfileResponse(ProfileVO data) {
        this.data = data;
    }

    private ProfileVO data;
}
