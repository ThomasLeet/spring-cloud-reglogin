package com.thomas.user.profile.dto;

import com.thomas.user.profile.model.UserProfile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericProfilesResponse extends InternalBaseRespose {

    public GenericProfilesResponse(String message, int code, String type, Map<Long,UserProfile> data) {
        super(message, code, type);
        this.data = data;
    }

    public GenericProfilesResponse(Map<Long,UserProfile> data) {
        this.data = data;
    }

    private Map<Long,UserProfile> data;
}
