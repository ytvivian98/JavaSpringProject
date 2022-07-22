package com.newcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author yt
 * date 2022-07-21
 * 自定义实体，用于存放从es中搜索出来的帖子的结果
 */
@Data
@ToString
@AllArgsConstructor
public class SearchResult {
    private List<DiscussPost> list;
    private long total;
}
