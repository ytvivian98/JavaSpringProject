package com.newcoder.community.service;

import com.newcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author yt
 * date 2022-07-14
 */
@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId, int entityType, int entityId, int entityUserId){
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
//        //是否已经点赞
//        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
//        if(isMember){
//            //已经点赞就取消
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }
//        else {
//            //未点赞就点赞
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
        //加入事务操作，因为有多个插入等
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                boolean isMember = operations.opsForSet().isMember(entityLikeKey,userId);
                operations.multi(); //开启事务
                if(isMember){
                    operations.opsForSet().remove(entityLikeKey,userId);
                    operations.opsForValue().decrement(userLikeKey);
                }
                else {
                    operations.opsForSet().add(entityLikeKey,userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    //查询实体的点赞数量
    public long findEntityLikeCount(int entityType, int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //查询某人对某实体的点赞状态（是否点过赞）
    public int findEntityLikeStatus(int userId, int entityType, int entityId)
    {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        //是否已经点赞
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId)?1:0;
    }

    //查询某个用户获得的赞
    public int findUserLikeCount(int userId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null?0:count.intValue();
    }

}
