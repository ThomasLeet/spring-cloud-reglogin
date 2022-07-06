package com.thomas.user.account.service;

import com.thomas.common.constants.EmailConstants;
import com.thomas.common.utils.IdHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.thomas.user.account.service.ProfileService.logger;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

    public boolean sendHtmlMail(String to,Long uid) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        String code = IdHelper.encodeEmailId(uid);
        try {
            messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            message.setSubject(EmailConstants.TITLE);
            String content = StringUtils.replace(EmailConstants.CONTENT,"{code}", code);;
            messageHelper.setText(content,true);
            javaMailSender.send(message);
            logger.info("The email has been sent, email={}, code={}",new Object[]{to,code});
            return true;

        } catch (Exception e) {
            logger.info("The email sent error, email={}, code={}",new Object[]{to,code});
        }
        return false;
    }
}
