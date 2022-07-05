package com.newcoder.community;

import com.newcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author yt
 * date 2022-07-05
 */
@SpringBootTest
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("1401256003@qq.com","TEST","Welcome");
    }

    @Test
    public void testHTMLMail(){
        Context context = new Context();
        context.setVariable("username","Lucy");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("1401256003@qq.com","HTML",content);
    }
}
