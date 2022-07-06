package com.thomas.user.account.dao;

import com.thomas.user.account.AccountApplication;
import com.thomas.user.account.model.UserAuth;
import com.thomas.user.account.utils.DeleteStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = {"dev"})
@SpringBootTest(classes = {AccountApplication.class, AccountDaoTest.class})
public class AccountDaoTest {
    @Autowired
    UserAuthMapper userAuthMapper;

    @Test
    @Ignore
    public void test() {
        UserAuth userAuth = new UserAuth();
        userAuth.setUid(1L);
        userAuth.setEmail("thomas_lee007@hotmail.com");
        userAuth.setPhone("13661395951");
        userAuth.setCountryCode("86");
        userAuth.setActivated(true);
        userAuth.setDeleted(DeleteStatus.DEFAULT_STATUS);
        userAuth.setCreateTime(System.currentTimeMillis());
        userAuth.setUpdateTime(System.currentTimeMillis());
        userAuthMapper.insert(userAuth);
    }
}
