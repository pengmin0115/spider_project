package com.spider.jsoup;

import java.util.Objects;

/**
 * @author pengmin
 * @date 2021/4/2 9:25
 */

public class StringUtils {

    public static boolean isNullOrEmpty(String str){
        if (Objects.isNull(str)){
            return true;
        }
        return (str.length() == 0)  || ("null".equals(str));
    }
}
