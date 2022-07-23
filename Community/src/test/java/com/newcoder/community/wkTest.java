package com.newcoder.community;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author yt
 * date 2022-07-23
 */
@SpringBootTest
public class wkTest {
    public static void main(String[] args) {
        String cmd = "D:\\Software\\wkhtmltopdf\\bin\\wkhtmltoimage --quality 75 https://www.bilibili.com d:\\javaTestCache\\wk-img\\3.png";

        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
