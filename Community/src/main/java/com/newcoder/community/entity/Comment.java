package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author yt
 * date 2022-07-11
 */
@Data
@ToString
public class Comment {
    private Integer id;
    private int userId;
    private int entityId;
    private int entityType;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;
}
