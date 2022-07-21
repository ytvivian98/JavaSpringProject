package com.newcoder.community.dao.elasticsearch;

import com.newcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yt
 * date 2022-07-20
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer/*主键的类型*/> {
}
