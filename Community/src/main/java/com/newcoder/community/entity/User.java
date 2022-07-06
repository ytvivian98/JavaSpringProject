package com.newcoder.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yt
 * date 2022-06-29
 */
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private int type;

    private int status;

    private String activationCode;

    private String headerUrl;

    private Date createTime;
}
