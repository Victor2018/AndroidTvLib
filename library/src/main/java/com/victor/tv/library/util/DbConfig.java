package com.victor.tv.library.util;

/**
 *  数据库配置信息
 * Created by victor on 2017/11/3.
 */
public class DbConfig {
    public static final String DB_NAME 						= "tv_db";
    public static final String SCHEME 						= "content://";
    public static final String AUTHORITY 					= "content.tv.content";
    public static final String URI_PATH 					    = SCHEME + AUTHORITY + "/";
    public static final int DB_VERSION 						= 1;

    public static class TB {
        public static final String MATCH_SEARCH_HISTORY 		 = "match_search_history";
    }
}

