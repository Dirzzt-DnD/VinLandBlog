package com.warzero.vinlandblog.constants;

public class SystemConstants {
    /**
     * 文章是正常发布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";

    /**
     * 目录是正常发布状态
     */
    public static final String Category_STATUS_NORMAL = "0";

    /**
     * 友链是审核通过状态
     */
    public static final String LINK_STATUS_PASS = "0";

    /**
     * 根节点评论
     */
    public static final Long COMMENT_ROOT = -1L;

    /**
     * Redis 用户 id 的前奏
     */
    public static final String REDIS_USER_ID_PREFIX = "bloglogin:";

    public static final String ADMIN_USER = "1";

    public static final String ARTICLE_STATUS_DRAFT = "1";
}
