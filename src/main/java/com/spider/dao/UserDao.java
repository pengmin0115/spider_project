package com.spider.dao;

import com.spider.jsoup.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author pengmin
 * @date 2021/4/2 20:08
 */
@Mapper
public interface UserDao {
    /**
     * 插入数据;
     * @param userInfo
     */
    void insert(UserInfo userInfo);

    /**
     * 获取用户;
     * @param username
     * @return
     */
    UserInfo getUserByName(String username);

    /**
     * 查询所有用户;
     * @return
     */
    ArrayList<UserInfo> getAllUserInfo();
}
