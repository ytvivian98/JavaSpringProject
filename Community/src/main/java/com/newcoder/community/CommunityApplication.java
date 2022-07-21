package com.newcoder.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.newcoder.community.dao")
public class CommunityApplication {

    @PostConstruct
    public void init(){
        //解决netty启动冲突问题
        //Netty4Utils中的
        System.setProperty("es.set.netty.runtime.avaliable.processors","false");
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
