package com.newcoder.community.config;

import com.newcoder.community.controller.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author yt
 * date 2022-07-23
 */
@Configuration
public class QuartzConfig {

    //刷新帖子分数的任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("PostScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);//设置  job 是否是持久性的，默认为 false.
        factoryBean.setRequestsRecovery(true);//设置  job 遇到故障重启后，是否是可恢复的，默认为 false
        return factoryBean;
    }
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail){
        SimpleTriggerFactoryBean factoryBean= new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail);
        factoryBean.setName("PostScoreRefreshTrigger");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setRepeatInterval(1000*60*5);
        factoryBean.setJobDataMap(new JobDataMap());    //用于存储 job 实例状态信息
        return factoryBean;
    }
}
