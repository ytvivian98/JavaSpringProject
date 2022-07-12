package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author yt
 * date 2022-07-12
 */
@Data
@ToString
public class Message {
    private Integer id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;

}
