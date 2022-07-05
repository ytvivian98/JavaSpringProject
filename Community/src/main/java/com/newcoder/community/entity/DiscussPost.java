package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author yt
 * date 2022-06-30
 */

@Data
@ToString
public class DiscussPost {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private Integer type;

    private Integer status;

    private Timestamp createTime;

    private Integer commentCount;

    private Double score;
}
