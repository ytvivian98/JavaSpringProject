package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author yt
 * date 2022-07-06
 */
@Data
@ToString
public class LoginTicket {

    private Integer id;

    private Integer userId;

    private String ticket;

    private Integer status;

    private Date expired;
}
