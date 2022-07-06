package com.thomas.user.account.profile;

import com.thomas.user.profile.ProfileApplication;
import com.thomas.user.profile.dao.UserProfileMapper;
import com.thomas.user.profile.model.UserProfile;
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
@SpringBootTest(classes = {ProfileApplication.class, UserProfileDaoTest.class})
public class UserProfileDaoTest {
    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    @Ignore
    public void insert() {
        UserProfile userProfile = new UserProfile();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);
        userProfile.setNickname("test1");
        userProfile.setCreateTime(System.currentTimeMillis());
        userProfile.setUpdateTime(System.currentTimeMillis());
        userProfile.setIcon("https://www.iqiyipic.com/common/fix/site-v5/header-userImg-default-dark.png");
        userProfileMapper.insert(userProfile);

        userProfile.setUid(2222L);
        userProfile.setNickname("test2");
        userProfileMapper.insert(userProfile);
    }

    @Test
    @Ignore
    public void update() {
        UserProfile userProfile = new UserProfile();
        userProfile.setActivated(true);
        userProfile.setDeleted(false);
        userProfile.setUid(1111L);
        userProfile.setNickname("test11");
        userProfile.setCreateTime(System.currentTimeMillis());
        userProfile.setUpdateTime(System.currentTimeMillis());
        userProfileMapper.updateByPrimaryKeySelective(userProfile);
    }

    @Test
    @Ignore
    public void delete() {
        List<Long> uids = new ArrayList<>();
        uids.add(1111L);
        uids.add(2222L);
        userProfileMapper.updateStatusByUids(uids,true,System.currentTimeMillis());
    }

}
