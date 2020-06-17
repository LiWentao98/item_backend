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

    /**
     * redis中存储学科信息前缀
     */
    public static final String REDIS_SUBJECT = "SUBJECT_";
    public static final String REDIS_NOTICE = "NOTICE_";//超级管理员的学校id 0 ，notice 后面是学校id
    public static final String REDIS_NOTICE_SERVICE = "NOTICESERVICE__";//超级管理员的学校id 0 ，notice 后面是学校id


}