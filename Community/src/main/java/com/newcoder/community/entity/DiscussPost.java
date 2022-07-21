package com.newcoder.community.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author yt
 * date 2022-06-30
 */

@Data
@ToString
@Setting(shards = 6, replicas = 3)
@Document(indexName = "discusspost")
public class DiscussPost {
    @Id
    private Integer id;

    @Field(type = FieldType.Integer)
    private int userId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)
    private int type;

    @Field(type = FieldType.Integer)
    private int status;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private int commentCount;

    @Field(type = FieldType.Double)
    private double score;
}
