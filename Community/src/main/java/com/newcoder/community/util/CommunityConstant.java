package com.newcoder.community.util;

/**
 * @author yt
 * date 2022-07-05
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证的超时的时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态下的登录凭证超时时间
     * 100天
     */
    int REMEMBER_EXPIRED_SECONDS =3600*100*24;

}