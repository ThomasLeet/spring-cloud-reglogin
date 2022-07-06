package com.thomas.user.account.profile;

import com.alibaba.fastjson.JSON;
import com.thomas.user.profile.ProfileApplication;
import com.thomas.user.profile.dao.UserProfileMapper;
import com.thomas.user.profile.model.UserProfile;
import com.thomas.user.profile.service.ProfileService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = {"dev"})
@SpringBootTest(classes = {ProfileApplication.class, UserProfileServiceTest.class})
public class UserProfileServiceTest {
    @Autowired
    ProfileService profileService;

    @Test
    @Ignore
    public void get(){
        UserProfile profile = profileService.getByUid(1111L,false);
        System.out.println(JSON.toJSON(profile));
        Assert.assertNotNull(profile);
    }
    @Test
    @Ignore
    public void insert() {
        UserProfile userProfile = new UserProfile();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);

        profileService.insert(userProfile);
//        userProfile.setUid(2222L);
//        userProfile.setNickname("test2");
//        profileService.insert(userProfile);
    }

    @Test
    @Ignore
    public void update() {
        UserProfile userProfile = new UserProfile();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);
        userProfile.setNickname("test11");
        profileService.update(userProfile);
    }

    @Test
    @Ignore
    public void deleteByUids() {
        List<Long> uids = new ArrayList<>();
        uids.add(1111L);
        uids.add(2222L);
        profileService.deleteByUids(uids);
    }

    @Test
    @Ignore
    public void delete() {
        profileService.delete(1111L);
    }

}
