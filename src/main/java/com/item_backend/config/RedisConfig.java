package com.item_backend.config;

/**
 * 配置redis的存取
 *
 */
public class RedisConfig {

    /**
     * redis中个人信息的最大数量
     */
    public static final int REDIS_USER_MESSAGE_COUNT = 30;

    /**
     * redis存储个人信息前缀
     */
    public static final String REDIS_USER_MESSAGE = "USERMESSAGE_";

    public static final String REDIS_QUESTION = "QUESTION_";

}