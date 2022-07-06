package com.thomas.user.account.dao;


import com.thomas.user.account.AccountApplication;
import com.thomas.user.account.service.EmailService;
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
public class EmailTest {

    @Autowired
    EmailService emailService;

    @Test
    @Ignore
    public void sendHtmlMail(){
        String content = "<a href='https://blog.csdn.net/'>你好，欢迎注册网站，请点击链接激活</a>";
        emailService.sendHtmlMail("thomas_lee007@163.com",1111L);
    }

}
