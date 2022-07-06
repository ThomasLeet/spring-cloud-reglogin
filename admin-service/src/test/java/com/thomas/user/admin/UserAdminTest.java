package com.thomas.user.admin;

import com.thomas.common.utils.PwdHelper;
import com.thomas.user.account.model.UserAuth;
import com.thomas.user.admin.dao.UserAdminMapper;
import com.thomas.user.admin.model.UserAdmin;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = {"dev"})
@SpringBootTest(classes = {AdminApplication.class, UserAdminTest.class})
public class UserAdminTest {

    @Autowired
    UserAdminMapper userAdminMapper;

    @Test
    @Ignore
    public void insert() {
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setCreateTime(System.currentTimeMillis());
        userAdmin.setUpdateTime(System.currentTimeMillis());
        userAdmin.setName("admin");
        userAdmin.setPassword(PwdHelper.calcPasswd("1234!@#$", PwdHelper.genPasswdSalt()));
        userAdminMapper.insert(userAdmin);
    }

    @Test
    @Ignore
    public void select(){
        UserAdmin userAdmin = userAdminMapper.selectByName("admin");
        Assert.assertNotNull(userAdmin);
        Assert.assertTrue( PwdHelper.checkPasswd("1234!@#$", userAdmin.getPassword()));
    }
}
