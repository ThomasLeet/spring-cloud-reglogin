package com.thomas.user.account.dao;

import com.alibaba.fastjson.JSON;
import com.thomas.user.account.AccountApplication;
import com.thomas.user.account.service.ProfileService;
import com.thomas.user.profile.dto.ProfileVO;
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
@SpringBootTest(classes = {AccountApplication.class, AccountDaoTest.class})
public class ProfileServiceTest {
    
    
    @Autowired
    ProfileService profileService;
    @Test
    @Ignore
    public void  getUid() {
        Long uid = 1111L;
        ProfileVO propfileVO = profileService.getUid(uid);
        System.out.println(JSON.toJSON(propfileVO));
    }

    @Test
    @Ignore
    public void getInternalUid() {
        Long uid = 1111L;
        ProfileVO propfileVO = profileService.getInternalUid(uid,true);
        System.out.println(JSON.toJSON(propfileVO));
    }

    @Test
    @Ignore
    public void  create() {
        ProfileVO userProfile = new ProfileVO();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);
        ProfileVO propfileVO = profileService.create(userProfile);
        JSON.toJSON(propfileVO);
//        userProfile.setUid(2222L);
//        userProfile.setNickname("test2");
//        propfileVO = profileService.create(userProfile);
//        System.out.println(JSON.toJSON(propfileVO));

    }

    @Test
    @Ignore
    public void  update() {
        ProfileVO userProfile = new ProfileVO();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);
        userProfile.setNickname("test11");
        ProfileVO propfileVO = profileService.update(userProfile);
        System.out.println(JSON.toJSON(propfileVO));
    }

    @Test
    @Ignore
    public void  delete() {
        Long uid = 1111L;
        boolean deleted = profileService.delete(uid);
        System.out.println(JSON.toJSON(deleted));
    }

    @Test
    @Ignore
    public void  batchDelete() {
        List<Long> uids = new ArrayList<>();
        uids.add(1111L);
        uids.add(2222L);
        boolean deleted = profileService.batchDelete(uids);
        System.out.println(JSON.toJSON(deleted));
    }
}
