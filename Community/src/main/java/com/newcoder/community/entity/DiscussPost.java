package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author yt
 * date 2022-06-30
 */

@Data
@ToString
public class DiscussPost {
    private Integer id;

    private int userId;

    private String title;

    private String content;

    private int type;

    private int status;

    private Date createTime;

    private int commentCount;

    private double score;
}
