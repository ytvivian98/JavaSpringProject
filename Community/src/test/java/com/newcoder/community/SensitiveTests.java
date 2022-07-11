package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yt
 * date 2022-07-10
 */
@SpringBootTest
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "赌博，嘿嘿嘿赌+++博";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

}
